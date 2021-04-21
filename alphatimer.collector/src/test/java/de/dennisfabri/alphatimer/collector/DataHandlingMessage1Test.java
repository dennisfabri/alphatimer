package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.events.dropped.UnstructuredInputDroppedEvent;
import de.dennisfabri.alphatimer.api.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.events.messages.enums.KindOfTime;
import de.dennisfabri.alphatimer.api.events.messages.enums.MessageType;
import de.dennisfabri.alphatimer.api.events.messages.enums.RankInfo;
import de.dennisfabri.alphatimer.api.events.messages.enums.TimeType;
import de.dennisfabri.alphatimer.api.events.messages.values.UsedLanes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.BitSet;

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.*;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage1Test {

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
    void sendMessage1() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        for (byte b : message1) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[18] = 0x31;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        for (byte b : bogus) {
            alphaTranslator.put(b);
        }
        for (byte b : message1) {
            alphaTranslator.put(b);
        }

        verify(listener, times(2)).notify(Mockito.any());
        verify(listener, times(1)).notify(new UnstructuredInputDroppedEvent(bogus));
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
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
