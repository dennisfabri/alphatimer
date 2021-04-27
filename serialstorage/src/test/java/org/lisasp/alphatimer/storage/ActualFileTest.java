package org.lisasp.alphatimer.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActualFileTest {

    private ActualFile actualFile;
    private String filename;

    @BeforeEach
    void prepare() throws IOException {
        File tempFile = File.createTempFile("ActualFileTest-", "-test");
        tempFile.deleteOnExit();

        filename = tempFile.getCanonicalPath();
        actualFile = new ActualFile();

        if (!tempFile.delete()) {
            throw new IOException("Could not delete temporary file.");
        }
    }

    @AfterEach
    void cleanup() throws IOException {
        if (Files.exists(Path.of(filename))) {
            Files.delete(Path.of(filename));
        }

        actualFile = null;
        filename = null;
    }

    private long getFileSize() throws IOException {
        return Files.size(Path.of(filename));
    }

    @Test
    void write1() throws IOException {
        actualFile.write(filename, (byte) 0x01);

        assertEquals(1, getFileSize());
    }

    @Test
    void write2() throws IOException {
        actualFile.write(filename, (byte) 0x04);
        actualFile.write(filename, (byte) 0x03);
        actualFile.write(filename, (byte) 0x02);
        actualFile.write(filename, (byte) 0x01);

        assertEquals(4, getFileSize());
    }

    @Test
    void read() throws IOException {
        Files.write(Path.of(filename),
                    new byte[]{0x02, 0x03, 0x05, 0x06},
                    StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE);

        byte[] actual = actualFile.read(filename);

        assertArrayEquals(new byte[]{0x02, 0x03, 0x05, 0x06}, actual);
    }

    @Test
    void readNonExisting() throws IOException {
        Files.deleteIfExists(Path.of(filename));
        byte[] actual = actualFile.read(filename);
        assertArrayEquals(new byte[0], actual);
    }

    @Test
    void getSeparatorTest() {
        assertEquals(File.separator, actualFile.getSeparator());
    }
}
