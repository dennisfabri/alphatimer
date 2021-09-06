package org.lisasp.alphatimer.datatests;

import org.lisasp.alphatimer.legacy.LegacySerialization;
import org.lisasp.alphatimer.legacy.dto.Heat;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestData {

    private static final boolean verbose = true;

    private static final String TEST_DATA_DIRECTORY = "target/test-data/";

    private static final String TEST_DATA_ZIP = "testdata.zip";

    public void prepare() throws IOException {
        prepare("");
    }

    public void prepare(String filenameFilter) throws IOException {
        cleanup();

        var classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(TEST_DATA_ZIP)) {
            new TestData().unzipTestData(is, TEST_DATA_DIRECTORY, filenameFilter);
        }
    }

    public void cleanup() throws IOException {
        if (verbose) {
            return;
        }
        if (Files.exists(Path.of(TEST_DATA_DIRECTORY))) {
            Files.walkFileTree(Path.of(TEST_DATA_DIRECTORY),
                               new SimpleFileVisitor<>() {
                                   @Override
                                   public FileVisitResult postVisitDirectory(
                                           Path dir, IOException exc) throws IOException {
                                       Files.delete(dir);
                                       return FileVisitResult.CONTINUE;
                                   }

                                   @Override
                                   public FileVisitResult visitFile(
                                           Path file, BasicFileAttributes attrs)
                                           throws IOException {
                                       Files.delete(file);
                                       return FileVisitResult.CONTINUE;
                                   }
                               });
        }
        Files.deleteIfExists(Path.of(TEST_DATA_DIRECTORY));
    }

    private static String readFile(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file)) {
            return lines.collect(Collectors.joining("\n"));
        }
    }

    public String readLegacyData(String filename) throws IOException {
        return readFile(Path.of(TestData.TEST_DATA_DIRECTORY + filename + ".ez"));
    }

    public byte[] readSerialInput(String filename) throws IOException {
        return Files.readAllBytes(Path.of(TestData.TEST_DATA_DIRECTORY + filename + ".serial"));
    }

    public void writeToDebug(Heat[] actual, String filename) throws IOException {
        if (verbose) {
            try (var os = new FileOutputStream(TestData.TEST_DATA_DIRECTORY + filename + ".xml");
                 PrintStream ps = new PrintStream(os)) {
                ps.println(LegacySerialization.toXML(actual));
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
                if (!newFile.exists()) {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
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
