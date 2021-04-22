package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.protocol.DataListener;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Ping;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.*;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingTest {

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
    void sendMessage1And2() {
        for (byte b : message1) {
            alphaTranslator.put(b);
        }
        for (byte b : message2) {
            alphaTranslator.put(b);
        }

        verify(listener, times(2)).notify(Mockito.any());
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.Empty,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPing() {
        for (byte b : message1) {
            alphaTranslator.put(b);
        }
        for (byte b : ping) {
            alphaTranslator.put(b);
        }

        verify(listener, times(2)).notify(Mockito.any());
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.Empty,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));
        verify(listener, times(1)).notify(new Ping(new byte[]{0x54, 0x50}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2AndPing() {
        for (byte b : message2) {
            alphaTranslator.put(b);
        }
        for (byte b : ping) {
            alphaTranslator.put(b);
        }

        verify(listener, times(2)).notify(Mockito.any());
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Empty));
        verify(listener, times(1)).notify(new Ping(new byte[]{0x54, 0x50}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPingAndMessage2() {
        for (byte b : message1) {
            alphaTranslator.put(b);
        }
        for (byte b : ping) {
            alphaTranslator.put(b);
        }
        for (byte b : message2) {
            alphaTranslator.put(b);
        }

        verify(listener, times(3)).notify(Mockito.any());
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.Empty,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));
        verify(listener, times(1)).notify(new Ping(new byte[]{0x54, 0x50}));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPingAndBogusAndMessage2() {
        for (byte b : message1) {
            alphaTranslator.put(b);
        }
        for (byte b : ping) {
            alphaTranslator.put(b);
        }
        for (byte b : bogus) {
            alphaTranslator.put(b);
        }
        for (byte b : message2) {
            alphaTranslator.put(b);
        }

        verify(listener, times(4)).notify(Mockito.any());
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.Empty,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));
        verify(listener, times(1)).notify(new Ping(new byte[]{0x54, 0x50}));
        verify(listener, times(1)).notify(new UnstructuredInputDroppedEvent(bogus));
        verify(listener, times(1)).notify(new DataHandlingMessage2((byte) 1,
                                                                   (byte) 0,
                                                                   112853930,
                                                                   TimeInfo.Normal,
                                                                   TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }
}
