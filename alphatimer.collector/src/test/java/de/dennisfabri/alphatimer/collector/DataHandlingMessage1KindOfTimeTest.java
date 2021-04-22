package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.protocol.DataListener;
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

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.createUsedLanes;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage1KindOfTimeTest {

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
    void sendMessage1SplitTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x49;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.SplitTime,
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
    void sendMessage1Finish() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x41;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Finish,
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
    void sendMessage1TakeOverTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x44;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.TakeOverTime,
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
    void sendMessage1ReactionTimeAtStart() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x52;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.ReactionTimeAtStart,
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
    void sendMessage1Empty() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x20;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Empty,
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
    void sendMessage1_B() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x42;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.UnknownValueB,
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
