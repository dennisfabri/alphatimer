package org.lisasp.alphatimer.legacy;

import org.lisasp.alphatimer.legacy.model.Heat;
import org.lisasp.alphatimer.legacy.model.LaneStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeatTest {

    private static final String heatAsString = "Heat(lanes={0=0: 2:03,006 (BackupOfThePreviousRace), 1=1: 2:03,010 (RaceTimes), 2=2: 3:54,100 (ResultsOfThePreviousRace), 3=3: 5:41,000 (ResultsOfTheRace), 4=4: 7:36,000 (BackupOfThePreviousRace), 5=5: 9:07,800 (RunningTime), 6=6: 9:10,800 (RunningTime)}, event=2, heat=3, id=1)";

    @Test
    void toStringTest() {
        Heat heat = new Heat("1", 2, 3);
        heat.store(0, 123006, LaneStatus.BackupOfThePreviousRace);
        heat.store(1, 123010, LaneStatus.RaceTimes);
        heat.store(2, 234100, LaneStatus.ResultsOfThePreviousRace);
        heat.store(3, 341000, LaneStatus.ResultsOfTheRace);
        heat.store(4, 456000, LaneStatus.BackupOfThePreviousRace);
        heat.store(5, 547800, LaneStatus.RunningTime);
        heat.store(6, 550800, LaneStatus.RunningTime);
        assertEquals(heatAsString, heat.toString());
    }
}
