package org.lisasp.alphatimer.test.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.serial.Storage;
import org.lisasp.basics.jre.date.DateFacade;
import org.lisasp.basics.jre.io.FileFacade;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

class StorageTest {

    private Storage storage;
    DateFacade dateTimeFacade;
    private FileFacade fileFacade;

    private static final String baseDir = "target/test-storage";

    private static final Path serialFile = Path.of("target","test-storage", "2021-04-17.serial");


    @BeforeEach
    void prepare() throws IOException {
        dateTimeFacade = Mockito.mock(DateFacade.class);
        Mockito.when(dateTimeFacade.today()).thenReturn(LocalDate.of(2021, 4, 17));
        fileFacade = Mockito.mock(FileFacade.class);
        Mockito.when(fileFacade.get(any())).thenReturn(new byte[]{0x05, 0x06, 0x07, 0x08, 0x09});
        Mockito.when(fileFacade.get(serialFile)).thenReturn(new byte[]{0x01, 0x02, 0x03, 0x04});
        storage = new Storage(baseDir, fileFacade, dateTimeFacade);
    }

    @AfterEach
    void cleanup() {
        storage = null;
        dateTimeFacade = null;
        fileFacade = null;
    }

    @Test
    void read() throws IOException {
        byte[] data = storage.read();

        assertArrayEquals(new byte[]{0x01, 0x02, 0x03, 0x04}, data);

        verify(dateTimeFacade, times(1)).today();
        verify(fileFacade, times(1)).get(serialFile);

        verifyNoMoreInteractions(dateTimeFacade);
        verifyNoMoreInteractions(fileFacade);
    }

    @Test
    void write() throws IOException {
        storage.write((byte) 0x01);
        storage.write((byte) 0x02);
        storage.write((byte) 0x03);
        storage.write((byte) 0x04);

        verify(dateTimeFacade, times(4)).today();
        verify(fileFacade, times(1)).append(serialFile, (byte) 0x01);
        verify(fileFacade, times(1)).append(serialFile, (byte) 0x02);
        verify(fileFacade, times(1)).append(serialFile, (byte) 0x03);
        verify(fileFacade, times(1)).append(serialFile, (byte) 0x04);

        verifyNoMoreInteractions(dateTimeFacade);
        verifyNoMoreInteractions(fileFacade);
    }
}
