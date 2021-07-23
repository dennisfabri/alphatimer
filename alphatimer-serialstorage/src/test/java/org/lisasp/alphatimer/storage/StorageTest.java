package org.lisasp.alphatimer.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.jre.date.DateFacade;
import org.lisasp.alphatimer.jre.io.FileFacade;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StorageTest {

    private Storage storage;
    DateFacade dateTimeFacade;
    private FileFacade fileFacade;

    private static final String baseDir = "target/test-storage";


    @BeforeEach
    void prepare() throws IOException {
        dateTimeFacade = Mockito.mock(DateFacade.class);
        Mockito.when(dateTimeFacade.today()).thenReturn(LocalDate.of(2021, 4, 17));
        fileFacade = Mockito.mock(FileFacade.class);
        Mockito.when(fileFacade.read("target/test-storage/2021-04-17.serial")).thenReturn(new byte[]{0x01, 0x02, 0x03, 0x04});
        Mockito.when(fileFacade.read(anyString())).thenReturn(new byte[]{0x01, 0x02, 0x03, 0x04});
        Mockito.when(fileFacade.getSeparator()).thenReturn("/");
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
        verify(fileFacade, times(1)).read("target/test-storage/2021-04-17.serial");
        verify(fileFacade, atLeastOnce()).getSeparator();

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
        verify(fileFacade, times(1)).write("target/test-storage/2021-04-17.serial", (byte) 0x01);
        verify(fileFacade, times(1)).write("target/test-storage/2021-04-17.serial", (byte) 0x02);
        verify(fileFacade, times(1)).write("target/test-storage/2021-04-17.serial", (byte) 0x03);
        verify(fileFacade, times(1)).write("target/test-storage/2021-04-17.serial", (byte) 0x04);
        verify(fileFacade, atLeastOnce()).getSeparator();

        verifyNoMoreInteractions(dateTimeFacade);
        verifyNoMoreInteractions(fileFacade);
    }
}
