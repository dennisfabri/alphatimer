package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.events.dropped.DataHandlingMessage2DroppedEvent;
import de.dennisfabri.alphatimer.api.events.dropped.UnknownMessageDroppedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.message2;
import static org.mockito.Mockito.*;

class DataHandlingMessage2InvalidTest {

    private AlphaTranslator alphaTranslator;
    private DataListener listener;

    @BeforeEach
    void prepare() {
        alphaTranslator = new AlphaTranslator();
        listener = mock(DataListener.class);
        alphaTranslator.register(listener);
    }

    @AfterEach
    void cleanUp() {
        alphaTranslator = null;
        listener = null;
    }

    @Test
    void sendCorruptedMessage2_1() {
        // bytes at index 3 and 7 are required to detect message 2
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[3] = 0x00;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendCorruptedMessage2_2() {
        // bytes at index 3 and 7 are required to detect message 2
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[7] = 0x00;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteI() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[4] = 0x00;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteJ1() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[5] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteJ2() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[6] = 0x00;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK1() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[8] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK2() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[9] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK3() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[10] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK4() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[11] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK5() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[12] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK6() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[13] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK7() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[14] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK8() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[15] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK9() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[16] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK10() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[17] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK11() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[18] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithInvalidByteK12() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[19] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }
}
