package org.lisasp.alphatimer.refinedmessages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessageListener;
import org.lisasp.alphatimer.api.refinedmessages.accepted.ReadyToStartMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedReadyToStartMessage;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class ReadyToStartMessageTest {

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
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new ReadyToStartMessage(event, heat, lapCount));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidKindOfTime() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.SplitTime,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.SplitTime,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeType() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.CorrectedTime,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.CorrectedTime,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidRank() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 1,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 1,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidRankInfo() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Disqualified,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Disqualified,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidLane() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidCurrentLap() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeInMillis() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                12345,
                TimeInfo.Backup,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                12345,
                TimeInfo.Backup,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeInfo() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Backup,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Backup,
                TimeMarker.Empty)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeMarker() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedReadyToStartMessage(new DataHandlingMessage(
                "1", "2",
                MessageType.ReadyToStart,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }
}
