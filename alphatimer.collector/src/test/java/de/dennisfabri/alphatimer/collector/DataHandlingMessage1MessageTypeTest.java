package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
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

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.createUsedLanes;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage1MessageTypeTest {

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
    void sendMessage1ReadyToStart() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x30;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.ReadyToStart,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x31;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.OfficialEnd,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x32;

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
    void sendMessage1CurrentRaceResults() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x33;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.CurrentRaceResults,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x34;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.CurrentRaceResultsWithBackupTimes,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x35;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.PreviousRaceResults,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x36;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.PreviousRaceResultsWithBackupTimes,
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
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1, DataHandlingMessageTestData.message1.length);
        message1modified[3] = 0x37;

        for (byte b : message1modified) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).notify(new DataHandlingMessage1(MessageType.UnknownValue7,
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
