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
class DataHandlingMessage1TimeTypeTest {

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
    void sendMessage1CorrectedTime() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x43;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x45;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x46;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x49;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x4D;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x4E;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x54;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x2B;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x2D;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x20;

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
                (byte) 0,
                RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1_1() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[5] = 0x31;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OnLineTime,
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
