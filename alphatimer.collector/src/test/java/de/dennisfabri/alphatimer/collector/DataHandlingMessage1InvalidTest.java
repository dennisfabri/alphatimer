package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.events.dropped.DataHandlingMessage1DroppedEvent;
import de.dennisfabri.alphatimer.api.events.dropped.UnknownMessageDroppedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.message1;
import static org.mockito.Mockito.*;

class DataHandlingMessage1InvalidTest {

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
    void sendCorruptedMessage1_1() {
        // bytes at index 15 and 16 are required to detect message 1
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[15] = 0x00;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendCorruptedMessage1_2() {
        // bytes at index 15 and 16 are required to detect message 1
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[16] = 0x00;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteA1() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[3] = 0x00;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteA2() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[3] = 0x39;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteB() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[4] = 0x00;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteC() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[5] = 0x00;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteD1() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[6] = 0x00;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteD2() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[7] = 0x00;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteE1() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[8] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteE2() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[9] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteF1() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[10] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteF2() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[11] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteF3() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[12] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteG1() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[13] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteG2() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[14] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteH1() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[17] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithInvalidByteH2() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[18] = 0x40;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

}
