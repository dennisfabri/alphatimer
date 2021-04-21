package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.events.dropped.UnstructuredInputDroppedEvent;
import de.dennisfabri.alphatimer.api.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.events.messages.enums.TimeInfo;
import de.dennisfabri.alphatimer.api.events.messages.enums.TimeMarker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.bogus;
import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.message2;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage2Test {

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
    void sendMessage2() {
        for (byte b : message2) {
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
    void sendMessage2WithModifiedLap() {
        byte[] message2modified = Arrays.copyOf(message2, message2.length);
        message2modified[5] = 0x20;
        message2modified[6] = 0x31;

        for (byte b : message2modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 1,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2AfterSomeBogusData() {
        for (byte b : bogus) {
            alphaTranslator.put(b);
        }
        for (byte b : message2) {
            alphaTranslator.put(b);
        }

        verify(listener, times(2)).notify(Mockito.any());
        verify(listener, times(1)).notify(new UnstructuredInputDroppedEvent(bogus));
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage2.class));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }
}
