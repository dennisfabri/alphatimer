package org.lisasp.alphatimer.legacy;

import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.protocol.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.legacy.model.Heat;
import org.lisasp.alphatimer.legacy.model.LaneStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LegacyTimeStorageTest {

    LegacyTimeStorage timeStorage;

    @BeforeEach
    void prepare() {
        timeStorage = new LegacyTimeStorage();

        DataHandlingMessageAggregator aggregator = new DataHandlingMessageAggregator(timeStorage);
    }

    @AfterEach
    void cleanUp() {
        timeStorage = null;
    }

    private Heat getHeat(LaneStatus status) {
        Heat heat = new Heat("1", 2, 3);
        heat.store(0, 123450, status);
        return heat;
    }

    static UsedLanes createUsedLanes() {
        boolean[] lanes = new boolean[]{
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true};
        return new UsedLanes(lanes);
    }

    @Test
    void HeatWithOneResult_OnLineTime() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
    }

    @Test
    void HeatWithOneResult_PreviousRaceResultsWithBackupTimes() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.PreviousRaceResultsWithBackupTimes,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.BackupOfThePreviousRace), actual[0]);
    }

    @Test
    void HeatWithOneResult_PreviousRaceResults() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.PreviousRaceResults,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.ResultsOfThePreviousRace), actual[0]);
    }

    @Test
    void HeatWithOneResult_CurrentRaceResultsWithBackupTimes() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.CurrentRaceResultsWithBackupTimes,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.ResultsWithBackupTimes), actual[0]);
    }

    @Test
    void HeatWithOneResult_CurrentRaceResults() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.CurrentRaceResults,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.ResultsOfTheRace), actual[0]);
    }

    @Test
    void HeatWithOneResult_ReadyToStart() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.ReadyToStart,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.NotUsed), actual[0]);
    }

    @Test
    void HeatWithOneResult_UnknownValue7() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.UnknownValue7,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.NotUsed), actual[0]);
    }

    @Test
    void HeatWithoutResult() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.SplitTime,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(0, actual.length);
    }

    @Test
    void HeatWithoutResult_OfficialEnd() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OfficialEnd,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(0, actual.length);
    }

    @Test
    void OneHeat() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(1, actual.length);
        assertNotEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
    }

    @Test
    void TwoHeats() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 4,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(2, actual.length);
        assertEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
        assertNotEquals(getHeat(LaneStatus.RaceTimes), actual[1]);
    }

    @Test
    void TwoEvents() {
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 2,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        timeStorage.accept(new DataHandlingMessage(
                "1",
                "2",
                MessageType.OnLineTime,
                KindOfTime.Finish,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 1,
                (short) 3,
                (byte) 3,
                (byte) 0,
                RankInfo.Normal,
                (byte) 1,
                (byte) 2,
                123450,
                TimeInfo.Normal,
                TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(2, actual.length);
        assertEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
        assertNotEquals(getHeat(LaneStatus.RaceTimes), actual[1]);
    }
}
