package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.protocol.DataInputEventListener;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.DataHandlingMessage2DroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.Arrays;

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.message2;
import static org.mockito.Mockito.*;

class DataHandlingMessage2InvalidTest {

    private InputCollector alphaTranslator;
    private DataInputEventListener listener;

    @BeforeEach
    void prepare() {
        alphaTranslator = new InputCollector();
        listener = mock(DataInputEventListener.class);
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
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendCorruptedMessage2_2() {
        // bytes at index 3 and 7 are required to detect message 2
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[7] = 0x00;

        for (byte b : message2modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @ParameterizedTest
    @ValueSource(bytes = {4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19})
    void sendMessage2WithInvalidByte0x00(int byteIndex) {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[byteIndex] = 0x00;

        for (byte b : message2modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @ParameterizedTest
    @ValueSource(bytes = {4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19})
    void sendMessage2WithInvalidByte0x44(int byteIndex) {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[byteIndex] = 0x44;

        for (byte b : message2modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }
}
