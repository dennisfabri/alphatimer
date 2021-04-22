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
class DataHandlingMessage1TimeTypeTest {

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
    void sendMessage1CorrectedTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x43;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.CorrectedTime,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1DeletedTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x45;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.DeletedTime,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1FalseStartAtRelay() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x46;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.FalseStartAtRelay,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1InsertedTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x49;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.InsertedTime,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1ManualTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x4D;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.ManualTime,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1TimeIsMissing() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x4E;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.TimeIsMissing,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1OneOrTwoPushButtonTimesAreMissing() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x54;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.OneOrTwoPushButtonTimesAreMissing,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1PlatformTimeAfterTouchpadTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x2B;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.PlatformTimeAfterTouchpadTime,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1PlatformTimeBeforeTouchpadTime() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x2D;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.PlatformTimeBeforeTouchpadTime,
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
        message1modified[5] = 0x20;

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
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1_1() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x31;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                                   KindOfTime.Start,
                                                                   TimeType.UnkownValue1,
                                                                   createUsedLanes(),
                                                                   (byte) 2,
                                                                   (short) 1,
                                                                   (byte) 1,
                                                                   (byte) 0,
                                                                   RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }
}
