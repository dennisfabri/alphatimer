package org.lisasp.alphatimer.test.refinedmessages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.*;
import org.lisasp.alphatimer.api.ares.serial.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessageListener;
import org.lisasp.alphatimer.api.refinedmessages.accepted.DidNotStartMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedDidNotStartMessage;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class DidNotStartMessageTest {

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
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
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
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DidNotStartMessage(TIMESTAMP, "TestWK", event, heat, (byte) 1));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidKindOfTime() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.ReactionTimeAtStart,
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
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.ReactionTimeAtStart,
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
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeType() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.CorrectedTime,
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
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.CorrectedTime,
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
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidRank() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 1,
                RankInfo.Normal,
                (byte) 1,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 1,
                RankInfo.Normal,
                (byte) 1,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidRankInfo() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Disqualified,
                (byte) 1,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Disqualified,
                (byte) 1,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidLane() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
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

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
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

    @Test
    void invalidCurrentLap() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 1,
                0,
                TimeInfo.Normal,
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeInMillis() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
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
                12345,
                TimeInfo.Backup,
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
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
                12345,
                TimeInfo.Backup,
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void invalidTimeInfo() {
        final byte lapCount = 0;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
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
                TimeInfo.Backup,
                TimeMarker.DidNotStart));

        verify(listener, times(1)).accept(new DroppedDidNotStartMessage(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.CurrentRaceResults,
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
                TimeInfo.Backup,
                TimeMarker.DidNotStart)));
        verifyNoMoreInteractions(listener);
    }
}
