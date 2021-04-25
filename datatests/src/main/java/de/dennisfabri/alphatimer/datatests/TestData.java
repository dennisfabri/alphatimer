package de.dennisfabri.alphatimer.datatests;

import de.dennisfabri.alphatimer.legacy.LegacyXStreamUtil;
import de.dennisfabri.alphatimer.legacy.model.Heat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestData {

    private static final boolean verbose = false;

    private static final String TEST_DATA_DIRECTORY = "target/test-data/";

    private static final String TEST_DATA_ZIP = "testdata.zip";

    public void prepare() throws IOException {
        prepare("");
    }

    public void prepare(String filenameFilter) throws IOException {
        if (Files.exists(Path.of(TEST_DATA_DIRECTORY))) {
            return;
        }

        var classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(TEST_DATA_ZIP)) {
            new TestData().unzipTestData(is, TEST_DATA_DIRECTORY, filenameFilter);
        }
    }

    Heat[] readLegacyData(String filename) {
        return (Heat[]) LegacyXStreamUtil.getXStream().fromXML(new File(TestData.TEST_DATA_DIRECTORY + filename + ".ez"));
    }

    byte[] readSerialInput(String filename) throws IOException {
        return Files.readAllBytes(Path.of(TestData.TEST_DATA_DIRECTORY + filename + ".serial"));
    }

    void writeToDebug(Heat[] actual, String filename) throws IOException {
        if (verbose) {
            try (var os = new FileOutputStream(TestData.TEST_DATA_DIRECTORY + filename + ".xml")) {
                LegacyXStreamUtil.getXStream().toXML(actual, os);
            }
        }
    }

    /*
     * Source: https://www.baeldung.com/java-compress-and-uncompress
     */
    private void unzipTestData(InputStream fileZip, String destination, String filenameFilter) throws IOException {
        File destDir = new File(destination);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(fileZip);
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else if (filterByName(newFile, filenameFilter)) {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private boolean filterByName(File file, String filenameFilter) {
        if (filenameFilter == null || filenameFilter.trim().length() == 0) {
            return true;
        }
        return file.getName().equals(filenameFilter);
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        var destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
