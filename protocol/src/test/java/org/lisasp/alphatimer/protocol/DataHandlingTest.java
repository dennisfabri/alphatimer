package org.lisasp.alphatimer.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.mockito.Mockito;

import static org.lisasp.alphatimer.protocol.DataHandlingMessageTestData.*;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingTest {

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
    void sendMessage1And2() {
        for (byte b : message1) {
            alphaTranslator.accept(b);
        }
        for (byte b : message2) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(2)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1),
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        verify(listener, times(1)).accept(new DataHandlingMessage2(
                new String(message2),
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPing() {
        for (byte b : message1) {
            alphaTranslator.accept(b);
        }
        for (byte b : ping) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(2)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1),
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        verify(listener, times(1)).accept(new Ping(new byte[]{0x54, 0x50}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2AndPing() {
        for (byte b : message2) {
            alphaTranslator.accept(b);
        }
        for (byte b : ping) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(2)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage2(
                new String(message2),
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));
        verify(listener, times(1)).accept(new Ping(new byte[]{0x54, 0x50}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPingAndMessage2() {
        for (byte b : message1) {
            alphaTranslator.accept(b);
        }
        for (byte b : ping) {
            alphaTranslator.accept(b);
        }
        for (byte b : message2) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(3)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1),
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        verify(listener, times(1)).accept(new Ping(new byte[]{0x54, 0x50}));
        verify(listener, times(1)).accept(new DataHandlingMessage2(
                new String(message2),
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPingAndBogusAndMessage2() {
        for (byte b : message1) {
            alphaTranslator.accept(b);
        }
        for (byte b : ping) {
            alphaTranslator.accept(b);
        }
        for (byte b : bogus) {
            alphaTranslator.accept(b);
        }
        for (byte b : message2) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(4)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1),
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        verify(listener, times(1)).accept(new Ping(new byte[]{0x54, 0x50}));
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(bogus));
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
