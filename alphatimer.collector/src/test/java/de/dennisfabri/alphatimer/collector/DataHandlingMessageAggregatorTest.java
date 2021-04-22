package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.protocol.DataListener;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Ping;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.bogus;
import static de.dennisfabri.alphatimer.collector.DataHandlingMessageTestData.createUsedLanes;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessageAggregatorTest {

    private DataHandlingMessageAggregator aggregator;
    private DataListener listener;

    @BeforeEach
    void prepare() {
        aggregator = new DataHandlingMessageAggregator();
        listener = Mockito.mock(DataListener.class);
        aggregator.register(listener);
    }

    @AfterEach
    void cleanUp() {
        aggregator = null;
        listener = null;
    }

    @Test
    void sendMessage1And2() {
        aggregator.notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                   KindOfTime.Start,
                                                   TimeType.Empty,
                                                   createUsedLanes(),
                                                   (byte) 2,
                                                   (short) 1,
                                                   (byte) 1,
                                                   (byte) 0,
                                                   RankInfo.Normal));
        aggregator.notify(new DataHandlingMessage2((byte) 1,
                                                   (byte) 0,
                                                   112853930,
                                                   TimeInfo.Normal,
                                                   TimeMarker.Empty));

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(new DataHandlingMessage(MessageType.OnLineTime,
                                                                  KindOfTime.Start,
                                                                  TimeType.Empty,
                                                                  createUsedLanes(),
                                                                  (byte) 2,
                                                                  (short) 1,
                                                                  (byte) 1,
                                                                  (byte) 0,
                                                                  RankInfo.Normal,
                                                                  (byte) 1,
                                                                  (byte) 0,
                                                                  112853930,
                                                                  TimeInfo.Normal,
                                                                  TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPing() {
        aggregator.notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                   KindOfTime.Start,
                                                   TimeType.Empty,
                                                   createUsedLanes(),
                                                   (byte) 2,
                                                   (short) 1,
                                                   (byte) 1,
                                                   (byte) 0,
                                                   RankInfo.Normal));
        aggregator.notify(new Ping(new byte[]{0x12, 0x39}));

        verify(listener, times(0)).notify(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2AndPing() {
        aggregator.notify(new DataHandlingMessage2((byte) 1,
                                                   (byte) 0,
                                                   112853930,
                                                   TimeInfo.Normal,
                                                   TimeMarker.Empty));
        aggregator.notify(new Ping(new byte[]{0x12, 0x39}));

        verify(listener, times(0)).notify(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPingAndMessage2() {
        aggregator.notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                   KindOfTime.Start,
                                                   TimeType.Empty,
                                                   createUsedLanes(),
                                                   (byte) 2,
                                                   (short) 1,
                                                   (byte) 1,
                                                   (byte) 0,
                                                   RankInfo.Normal));
        aggregator.notify(new Ping(new byte[]{0x12, 0x39}));
        aggregator.notify(new DataHandlingMessage2((byte) 1,
                                                   (byte) 0,
                                                   112853930,
                                                   TimeInfo.Normal,
                                                   TimeMarker.Empty));

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(new DataHandlingMessage(MessageType.OnLineTime,
                                                                  KindOfTime.Start,
                                                                  TimeType.Empty,
                                                                  createUsedLanes(),
                                                                  (byte) 2,
                                                                  (short) 1,
                                                                  (byte) 1,
                                                                  (byte) 0,
                                                                  RankInfo.Normal,
                                                                  (byte) 1,
                                                                  (byte) 0,
                                                                  112853930,
                                                                  TimeInfo.Normal,
                                                                  TimeMarker.Empty));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPingAndBogusAndMessage2() {
        aggregator.notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                   KindOfTime.Start,
                                                   TimeType.Empty,
                                                   createUsedLanes(),
                                                   (byte) 2,
                                                   (short) 1,
                                                   (byte) 1,
                                                   (byte) 0,
                                                   RankInfo.Normal));
        aggregator.notify(new Ping(new byte[]{0x12, 0x39}));
        aggregator.notify(new UnstructuredInputDroppedEvent(bogus));
        aggregator.notify(new DataHandlingMessage2((byte) 1,
                                                   (byte) 0,
                                                   112853930,
                                                   TimeInfo.Normal,
                                                   TimeMarker.Empty));

        verify(listener, times(0)).notify(Mockito.any());

        verifyNoMoreInteractions(listener);
    }
}
