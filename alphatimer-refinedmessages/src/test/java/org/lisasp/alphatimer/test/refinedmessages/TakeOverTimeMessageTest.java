package org.lisasp.alphatimer.test.refinedmessages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessageListener;
import org.lisasp.alphatimer.api.refinedmessages.accepted.TakeOverTimeMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.UsedLanesMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedTakeOverTimeMessage;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TakeOverTimeMessageTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 6, 1, 10, 0);

    private DataHandlingMessageRefiner refiner;
    private RefinedMessageListener listener;

    @BeforeEach
    void prepare() {
        listener = Mockito.mock(RefinedMessageListener.class);

        refiner = new DataHandlingMessageRefiner();
        refiner.register(listener);
    }

    @Test
    void valid() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.OnLineTime,
                KindOfTime.TakeOverTime,
                TimeType.PlatformTimeAfterTouchpadTime,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 2,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.Plus));

        verify(listener, times(1)).accept(new TakeOverTimeMessage(TIMESTAMP,
                                                                  "TestWK",
                                                                  event,
                                                                  heat,
                                                                  RefinedMessageType.Live,
                                                                  (byte) 2,
                                                                  (byte) 1,
                                                                  TimeMarker.Plus,
                                                                  TimeType.PlatformTimeAfterTouchpadTime));
        verify(listener, times(1)).accept(new UsedLanesMessage(TIMESTAMP, "TestWK", event, heat, "1100000000"));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeType() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        DataHandlingMessage message = new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.OnLineTime,
                KindOfTime.TakeOverTime,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 2,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.Plus);

        refiner.accept(message);

        verify(listener, times(1)).accept(new DroppedTakeOverTimeMessage(message));
        verify(listener, times(1)).accept(new UsedLanesMessage(TIMESTAMP, "TestWK", event, heat, "1100000000"));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidRank() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        DataHandlingMessage message = new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.OnLineTime,
                KindOfTime.TakeOverTime,
                TimeType.PlatformTimeAfterTouchpadTime,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 15,
                RankInfo.Normal,
                (byte) 2,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.Plus);

        refiner.accept(message);

        verify(listener, times(1)).accept(new DroppedTakeOverTimeMessage(message));
        verify(listener, times(1)).accept(new UsedLanesMessage(TIMESTAMP, "TestWK", event, heat, "1100000000"));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidRankInfo() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;
        DataHandlingMessage message = new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.OnLineTime,
                KindOfTime.TakeOverTime,
                TimeType.PlatformTimeAfterTouchpadTime,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 2,
                (byte) 1,
                0,
                TimeInfo.UnknownAsterisk,
                TimeMarker.Plus);

        refiner.accept(message);

        verify(listener, times(1)).accept(new DroppedTakeOverTimeMessage(message));
        verify(listener, times(1)).accept(new UsedLanesMessage(TIMESTAMP, "TestWK", event, heat, "1100000000"));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidCurrentLap() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        DataHandlingMessage message = new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.OnLineTime,
                KindOfTime.TakeOverTime,
                TimeType.PlatformTimeAfterTouchpadTime,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 2,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Plus);

        refiner.accept(message);

        verify(listener, times(1)).accept(new DroppedTakeOverTimeMessage(message));
        verify(listener, times(1)).accept(new UsedLanesMessage(TIMESTAMP, "TestWK", event, heat, "1100000000"));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeMarker() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        DataHandlingMessage message = new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.OnLineTime,
                KindOfTime.TakeOverTime,
                TimeType.PlatformTimeAfterTouchpadTime,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 2,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty);

        refiner.accept(message);

        verify(listener, times(1)).accept(new DroppedTakeOverTimeMessage(message));
        verify(listener, times(1)).accept(new UsedLanesMessage(TIMESTAMP, "TestWK", event, heat, "1100000000"));
        verifyNoMoreInteractions(listener);
    }
}
