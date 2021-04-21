package de.dennisfabri.alphatimer.datatests;

import de.dennisfabri.alphatimer.legacy.XStreamUtil;
import de.dennisfabri.alphatimer.legacy.model.Heat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class Testdata {

    private final static boolean verbose = false;

    private static final String TESTDATA_DIRECTORY = "target/test-data/";

    void prepare() throws IOException {
        String zipfile = "testdata.zip";

        if (Files.exists(Path.of(TESTDATA_DIRECTORY))) {
            return;
        }

        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream is = classLoader.getResourceAsStream(zipfile)) {
            new Testdata().unzipTestData(is, TESTDATA_DIRECTORY);
        }
    }

    Heat[] readLegacyData(String filename) throws IOException {
        return (Heat[]) XStreamUtil.getXStream().fromXML(new File(Testdata.TESTDATA_DIRECTORY + filename + ".ez"));
    }

    byte[] readSerialInput(String filename) throws IOException {
        return Files.readAllBytes(Path.of(Testdata.TESTDATA_DIRECTORY + filename + ".serial"));
    }

    void writeToDebug(Heat[] actual, String filename) throws IOException {
        if (verbose) {
            try (FileOutputStream os = new FileOutputStream(Testdata.TESTDATA_DIRECTORY + filename + ".xml")) {
                XStreamUtil.getXStream().toXML(actual, os);
            }
        }
    }

    /**
     * @Source https://www.baeldung.com/java-compress-and-uncompress
     */
    private void unzipTestData(InputStream fileZip, String destination) throws IOException {
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
            } else {
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

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
