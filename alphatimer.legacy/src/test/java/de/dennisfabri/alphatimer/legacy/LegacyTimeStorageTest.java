package de.dennisfabri.alphatimer.legacy;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.*;
import de.dennisfabri.alphatimer.api.protocol.events.messages.values.UsedLanes;
import de.dennisfabri.alphatimer.collector.DataHandlingMessageAggregator;
import de.dennisfabri.alphatimer.legacy.model.Heat;
import de.dennisfabri.alphatimer.legacy.model.LaneStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LegacyTimeStorageTest {

    LegacyTimeStorage timeStorage;

    @BeforeEach
    void prepare() {
        timeStorage = new LegacyTimeStorage();

        DataHandlingMessageAggregator aggregator = new DataHandlingMessageAggregator();
        aggregator.register(timeStorage);
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

    private static UsedLanes createUsedLanes() {
        BitSet lanes = new BitSet();
        lanes.set(0, true);
        lanes.set(1, true);
        lanes.set(2, true);
        lanes.set(3, true);
        lanes.set(4, true);
        lanes.set(7, true);
        lanes.set(8, true);
        lanes.set(9, true);
        return new UsedLanes(lanes);
    }

    @Test
    void NoHeat1() {
        timeStorage.notify(new DataHandlingMessage1(MessageType.OnLineTime,
                                                    KindOfTime.Finish,
                                                    TimeType.Empty,
                                                    createUsedLanes(),
                                                    (byte) 1,
                                                    (short) 2,
                                                    (byte) 3,
                                                    (byte) 0,
                                                    RankInfo.Normal));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(0, actual.length);
    }

    @Test
    void NoHeat2() {
        timeStorage.notify(new DataHandlingMessage2((byte) 1, (byte) 2, 123450, TimeInfo.Normal, TimeMarker.Empty));
        Heat[] actual = timeStorage.getHeats();

        assertEquals(0, actual.length);
    }

    @Test
    void HeatWithOneResult_OnLineTime() {
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.PreviousRaceResultsWithBackupTimes,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.PreviousRaceResults,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.CurrentRaceResultsWithBackupTimes,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.CurrentRaceResults,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.ReadyToStart,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.UnknownValue7,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OfficialEnd,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
        timeStorage.notify(new DataHandlingMessage(MessageType.OnLineTime,
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
