package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.protocol.DataListener;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeInfo;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeMarker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage2TimeTest {

    private InputCollector alphaTranslator;
    private DataListener listener;

    @BeforeEach
    void prepare() {
        alphaTranslator = new InputCollector();
        listener = mock(DataListener.class);
        alphaTranslator.register(listener);
    }

    @AfterEach
    void cleanUp() {
        alphaTranslator = null;
        listener = null;
    }

    @Test
    void sendMessage2Empty() {
        for (byte b : DataHandlingMessageTestData.message2) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithMinus() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length + 1);
        System.arraycopy(DataHandlingMessageTestData.message2,
                         8,
                         message2modified,
                         9,
                         DataHandlingMessageTestData.message2.length - 8);
        message2modified[8] = 0x2D;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Minus));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithBackup() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length);
        message2modified[19] = 0x42;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Backup,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2Edited() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length);
        message2modified[19] = 0x45;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Edited,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2Asterisk() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length);
        message2modified[19] = 0x2A;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.UnknownAsterisk,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2Manual() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length);
        message2modified[19] = 0x4D;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Manual,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithTimePlaceholder() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length);
        message2modified[8] = 0x20;
        message2modified[9] = 0x20;
        message2modified[10] = 0x20;
        message2modified[11] = 0x20;
        message2modified[12] = 0x2B;
        message2modified[13] = 0x20;
        message2modified[14] = 0x2D;
        message2modified[15] = 0x2E;
        message2modified[16] = 0x2D;
        message2modified[17] = 0x2D;
        message2modified[18] = 0x20;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   0,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Plus));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithTimeDNS() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length);
        message2modified[8] = 0x20;
        message2modified[9] = 0x20;
        message2modified[10] = 0x20;
        message2modified[11] = 0x20;
        message2modified[12] = 0x20;
        message2modified[13] = 0x20;
        message2modified[14] = 0x20;
        message2modified[15] = 0x20;
        message2modified[16] = 0x44;
        message2modified[17] = 0x4E;
        message2modified[18] = 0x53;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   0,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.DidNotStart));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithTimeDNF() {
        byte[] message2modified = Arrays.copyOf(DataHandlingMessageTestData.message2,
                                                DataHandlingMessageTestData.message2.length);
        message2modified[8] = 0x20;
        message2modified[9] = 0x20;
        message2modified[10] = 0x20;
        message2modified[11] = 0x20;
        message2modified[12] = 0x20;
        message2modified[13] = 0x20;
        message2modified[14] = 0x20;
        message2modified[15] = 0x20;
        message2modified[16] = 0x44;
        message2modified[17] = 0x4E;
        message2modified[18] = 0x46;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   0,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.DidNotFinish));

        verifyNoMoreInteractions(listener);
    }
}
