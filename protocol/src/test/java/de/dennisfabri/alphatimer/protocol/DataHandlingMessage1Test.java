package de.dennisfabri.alphatimer.protocol;

import de.dennisfabri.alphatimer.api.protocol.DataInputEventListener;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.KindOfTime;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.MessageType;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.RankInfo;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static de.dennisfabri.alphatimer.protocol.DataHandlingMessageTestData.*;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage1Test {

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
    void sendMessage1() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        for (byte b : message1) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
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

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithRank1() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[18] = 0x31;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 1,
                RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AfterSomeBogusData() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        for (byte b : bogus) {
            alphaTranslator.accept(b);
        }
        for (byte b : message1) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(2)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(bogus));
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
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

        verifyNoMoreInteractions(listener);
    }
}
