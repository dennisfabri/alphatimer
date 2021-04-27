package org.lisasp.alphatimer.protocol;

import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.KindOfTime;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.MessageType;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.RankInfo;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.lisasp.alphatimer.protocol.DataHandlingMessageTestData.createUsedLanes;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage1KindOfTimeTest {

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
    void sendMessage1SplitTime() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x49;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x41;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x44;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x52;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x20;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[4] = 0x42;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
