package org.lisasp.alphatimer.test.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.DataHandlingMessage2DroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.lisasp.alphatimer.test.protocol.DataHandlingMessageTestData.message2;
import static org.mockito.Mockito.*;

class DataHandlingMessage2InvalidTest {

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
    void sendCorruptedMessage2_1() {
        // bytes at index 3 and 7 are required to detect message 2
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[3] = 0x00;

        for (byte b : message2modified) {
            inputCollector.accept(b);
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
            inputCollector.accept(b);
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
            inputCollector.accept(b);
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
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }
}
