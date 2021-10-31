package org.lisasp.alphatimer.test.legacy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.*;
import org.lisasp.alphatimer.api.ares.serial.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.legacy.dto.Heat;
import org.lisasp.alphatimer.legacy.dto.LaneStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LegacyServiceTest {

    public static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 6, 1, 10, 0);

    private LegacyService legacyService;

    @BeforeEach
    void prepare() {
        legacyService = new LegacyService(new TestLegacyRepository());
    }

    @AfterEach
    void cleanUp() {
        legacyService = null;
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
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
    }

    @Test
    void HeatWithOneResult_PreviousRaceResultsWithBackupTimes() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.BackupOfThePreviousRace), actual[0]);
    }

    @Test
    void HeatWithOneResult_PreviousRaceResults() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.ResultsOfThePreviousRace), actual[0]);
    }

    @Test
    void HeatWithOneResult_CurrentRaceResultsWithBackupTimes() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.ResultsWithBackupTimes), actual[0]);
    }

    @Test
    void HeatWithOneResult_CurrentRaceResults() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.ResultsOfTheRace), actual[0]);
    }

    @Test
    void HeatWithOneResult_ReadyToStart() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.NotUsed), actual[0]);
    }

    @Test
    void HeatWithOneResult_UnknownValue7() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertEquals(getHeat(LaneStatus.NotUsed), actual[0]);
    }

    @Test
    void HeatWithoutResult() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(0, actual.length);
    }

    @Test
    void HeatWithoutResult_OfficialEnd() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(0, actual.length);
    }

    @Test
    void OneHeat() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(1, actual.length);
        assertNotEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
    }

    @Test
    void TwoHeats() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(2, actual.length);
        assertEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
        assertNotEquals(getHeat(LaneStatus.RaceTimes), actual[1]);
    }

    @Test
    void TwoEvents() {
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        legacyService.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
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
        Heat[] actual = legacyService.getHeats();

        assertEquals(2, actual.length);
        assertEquals(getHeat(LaneStatus.RaceTimes), actual[0]);
        assertNotEquals(getHeat(LaneStatus.RaceTimes), actual[1]);
    }
}
