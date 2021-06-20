package org.lisasp.alphatimer.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeInfo;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeMarker;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.lisasp.alphatimer.protocol.DataHandlingMessageTestData.bogus;
import static org.lisasp.alphatimer.protocol.DataHandlingMessageTestData.message2;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage2Test {

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
    void sendMessage2() {
        for (byte b : message2) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).accept(
                new DataHandlingMessage2(
                        new String(message2),
                        (byte) 1,
                        (byte) 0,
                        112853930,
                        TimeInfo.Normal,
                        TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2WithModifiedLap() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[5] = 0x20;
        message2modified[6] = 0x31;

        for (byte b : message2modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).accept(new DataHandlingMessage2(
                new String(message2modified),
                (byte) 1,
                (byte) 1,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2AfterSomeBogusData() {
        for (byte b : bogus) {
            alphaTranslator.accept(b);
        }
        for (byte b : message2) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(2)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(bogus));
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).accept(new DataHandlingMessage2(
                new String(message2),
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }
}
