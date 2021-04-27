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
class DataHandlingMessage1MessageTypeTest {

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
    void sendMessage1ReadyToStart() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x30;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.ReadyToStart,
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
    void sendMessage1OfficialEnd() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x31;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.OfficialEnd,
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
    void sendMessage1OnLineTime() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x32;

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
    void sendMessage1CurrentRaceResults() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x33;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.CurrentRaceResults,
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
    void sendMessage1CurrentRaceResultsWithBackupTimes() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x34;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.CurrentRaceResultsWithBackupTimes,
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
    void sendMessage1PreviousRaceResults() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x35;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.PreviousRaceResults,
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
    void sendMessage1PreviousRaceResultsWithBackupTimes() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x36;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.PreviousRaceResultsWithBackupTimes,
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
    void sendMessage1_7() {
        DataInputEventListener listener = mock(DataInputEventListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x37;

        for (byte b : message1modified) {
            alphaTranslator.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                new String(message1modified),
                MessageType.UnknownValue7,
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
