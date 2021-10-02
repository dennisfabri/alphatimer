package org.lisasp.alphatimer.test.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.DataHandlingMessage1DroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.lisasp.alphatimer.test.protocol.DataHandlingMessageTestData.message1;
import static org.mockito.Mockito.*;

class DataHandlingMessage1InvalidTest {

    private InputCollector inputCollector;
    private DataInputEventListener listener;

    @BeforeEach
    void prepare() {
        DateTimeFacade dateTimeFacade = mock(DateTimeFacade.class);
        when(dateTimeFacade.now()).thenReturn(LocalDateTime.of(2021, 6, 21, 14, 53));

        listener = mock(DataInputEventListener.class);

        MessageConverter messageConverter = new MessageConverter();
        messageConverter.register(listener);

        inputCollector = new InputCollector("Test", dateTimeFacade);
        inputCollector.register(messageConverter);
    }

    @AfterEach
    void cleanUp() {
        inputCollector = null;
        listener = null;
    }

    @Test
    void sendCorruptedMessage1_1() {
        // bytes at index 15 and 16 are required to detect message 1
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[15] = 0x00;

        for (byte b : message1modified) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendCorruptedMessage1_2() {
        // bytes at index 15 and 16 are required to detect message 1
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[16] = 0x00;

        for (byte b : message1modified) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(UnknownMessageDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @ParameterizedTest
    @ValueSource(bytes = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 17, 18})
    void sendMessage1WithInvalidByte0x40(int byteIndex) {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[byteIndex] = 0x40;

        for (byte b : message1modified) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @ParameterizedTest
    @ValueSource(bytes = {3, 4, 5})
    void sendMessage1WithInvalidByte0x38(int byteIndex) {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[byteIndex] = 0x38;

        for (byte b : message1modified) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @ParameterizedTest
    @ValueSource(bytes = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 17, 18})
    void sendMessage1WithInvalidByte0x00(int byteIndex) {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[byteIndex] = 0x00;

        for (byte b : message1modified) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }
}
