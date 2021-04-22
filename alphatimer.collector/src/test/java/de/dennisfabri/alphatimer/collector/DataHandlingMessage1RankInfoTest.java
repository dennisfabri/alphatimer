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
class DataHandlingMessage1RankInfoTest {

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
        // message1modified[5] = 0x43;

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
    void sendMessage1WithoutRank() {
        DataListener listener = mock(DataListener.class);
        alphaTranslator.register(listener);

        // bytes at index 17 and 18 are set to "D "
        byte[] message1modified = Arrays.copyOf(DataHandlingMessageTestData.message1,
                                                DataHandlingMessageTestData.message1.length);
        message1modified[17] = 0x44;
        message1modified[18] = 0x20;

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
                                                                   RankInfo.UnknownValueD));

        verifyNoMoreInteractions(listener);
    }
}
