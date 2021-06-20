package org.lisasp.alphatimer.api.protocol.events.messages;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.KindOfTime;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.MessageType;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.RankInfo;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeType;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessage1Test {

    @Test
    void equalsTest() {
        DataHandlingMessage1 value1 = new DataHandlingMessage1("1", MessageType.OnLineTime,
                                                               KindOfTime.Start,
                                                               TimeType.Empty,
                                                               new UsedLanes(new boolean[]{false, false, false, false, false, false, false, false, false, false}),
                                                               (byte) 1,
                                                               (short) 2,
                                                               (byte) 3,
                                                               (byte) 4,
                                                               RankInfo.Normal);
        DataHandlingMessage1 value2 = new DataHandlingMessage1("1", MessageType.OnLineTime,
                                                               KindOfTime.Start,
                                                               TimeType.Empty,
                                                               new UsedLanes(new boolean[]{false, false, false, false, false, false, false, false, false, false}),
                                                               (byte) 1,
                                                               (short) 2,
                                                               (byte) 3,
                                                               (byte) 4,
                                                               RankInfo.Normal);
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage1 value1 = new DataHandlingMessage1("1",
                                                               MessageType.OnLineTime,
                                                               KindOfTime.Start,
                                                               TimeType.Empty,
                                                               new UsedLanes(new boolean[]{false, false, false, false, false, false, false, false, false, false}),
                                                               (byte) 1,
                                                               (short) 2,
                                                               (byte) 3,
                                                               (byte) 4,
                                                               RankInfo.Normal);
        DataHandlingMessage1 value2 = new DataHandlingMessage1("1",
                                                               MessageType.OnLineTime,
                                                               KindOfTime.SplitTime,
                                                               TimeType.Empty,
                                                               new UsedLanes(new boolean[]{false, false, false, false, false, false, false, false, false, false}),
                                                               (byte) 1,
                                                               (short) 2,
                                                               (byte) 3,
                                                               (byte) 4,
                                                               RankInfo.Normal);
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "DataHandlingMessage1(original=1, messageType=OnLineTime, kindOfTime=Start, timeType=Empty, usedLanes=UsedLanes(0000000000), lapCount=1, event=2, heat=3, rank=4, rankInfo=Normal)",
                new DataHandlingMessage1("1",
                                         MessageType.OnLineTime,
                                         KindOfTime.Start,
                                         TimeType.Empty,
                                         new UsedLanes(new boolean[]{false, false, false, false, false, false, false, false, false, false}),
                                         (byte) 1,
                                         (short) 2,
                                         (byte) 3,
                                         (byte) 4,
                                         RankInfo.Normal).toString());
    }
}
