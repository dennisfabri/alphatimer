package org.lisasp.alphatimer.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.lisasp.alphatimer.protocol.DataHandlingMessageTestData.bogus;
import static org.lisasp.alphatimer.protocol.DataHandlingMessageTestData.createUsedLanes;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class MessageAggregatorTest {

    private DataHandlingMessageAggregator aggregator;
    private DataHandlingMessageListener listener;

    @BeforeEach
    void prepare() {
        DateTimeFacade datetime = Mockito.mock(DateTimeFacade.class);
        Mockito.when(datetime.now()).thenReturn(LocalDateTime.of(2021, 6, 1, 10, 0));

        listener = Mockito.mock(DataHandlingMessageListener.class);
        aggregator = new MessageAggregator(datetime, "TestWK");
        aggregator.register(listener);
    }

    @AfterEach
    void cleanUp() {
        aggregator = null;
        listener = null;
    }

    @Test
    void register() {
        aggregator.accept(new DataHandlingMessage1(
                "1",
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        aggregator.accept(new DataHandlingMessage2(
                "2",
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage(
                "1",
                "2",
                LocalDateTime.of(2021, 6, 1, 10, 0),
                "TestWK",
                MessageType.OnLineTime,
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
    void sendMessage1And2() {
        aggregator.accept(new DataHandlingMessage1(
                "1",
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        aggregator.accept(new DataHandlingMessage2(
                "2",
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage(
                "1",
                "2",
                LocalDateTime.of(2021, 6, 1, 10, 0),
                "TestWK",
                MessageType.OnLineTime,
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
        aggregator.accept(new DataHandlingMessage1(
                "1",
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        aggregator.accept(new Ping(new byte[]{0x12, 0x39}));

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage2AndPing() {
        aggregator.accept(new DataHandlingMessage2(
                "2",
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));
        aggregator.accept(new Ping(new byte[]{0x12, 0x39}));

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPingAndMessage2() {
        aggregator.accept(new DataHandlingMessage1(
                "1",
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        aggregator.accept(new Ping(new byte[]{0x12, 0x39}));
        aggregator.accept(new DataHandlingMessage2(
                "2",
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new DataHandlingMessage(
                "1",
                "2",
                LocalDateTime.of(2021, 6, 1, 10, 0),
                "TestWK",
                MessageType.OnLineTime,
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
        aggregator.accept(new DataHandlingMessage1(
                "1",
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 0,
                RankInfo.Normal));
        aggregator.accept(new Ping(new byte[]{0x12, 0x39}));
        aggregator.accept(new UnstructuredInputDroppedEvent(bogus));
        aggregator.accept(new DataHandlingMessage2(
                "2",
                (byte) 1,
                (byte) 0,
                112853930,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }
}
