package de.dennisfabri.alphatimer.api.events.messages;

import de.dennisfabri.alphatimer.api.events.messages.enums.KindOfTime;
import de.dennisfabri.alphatimer.api.events.messages.enums.MessageType;
import de.dennisfabri.alphatimer.api.events.messages.enums.RankInfo;
import de.dennisfabri.alphatimer.api.events.messages.enums.TimeType;
import de.dennisfabri.alphatimer.api.events.messages.values.UsedLanes;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessage1Test {

    @Test
    void equalsTest() {
        DataHandlingMessage1 value1 = new DataHandlingMessage1(MessageType.OnLineTime,
                                                               KindOfTime.Start,
                                                               TimeType.Empty,
                                                               new UsedLanes(new BitSet()),
                                                               (byte) 1,
                                                               (short) 2,
                                                               (byte) 3,
                                                               (byte) 4,
                                                               RankInfo.Normal);
        DataHandlingMessage1 value2 = new DataHandlingMessage1(MessageType.OnLineTime,
                                                               KindOfTime.Start,
                                                               TimeType.Empty,
                                                               new UsedLanes(new BitSet()),
                                                               (byte) 1,
                                                               (short) 2,
                                                               (byte) 3,
                                                               (byte) 4,
                                                               RankInfo.Normal);
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage1 value1 = new DataHandlingMessage1(MessageType.OnLineTime,
                                                               KindOfTime.Start,
                                                               TimeType.Empty,
                                                               new UsedLanes(new BitSet()),
                                                               (byte) 1,
                                                               (short) 2,
                                                               (byte) 3,
                                                               (byte) 4,
                                                               RankInfo.Normal);
        DataHandlingMessage1 value2 = new DataHandlingMessage1(MessageType.OnLineTime,
                                                               KindOfTime.SplitTime,
                                                               TimeType.Empty,
                                                               new UsedLanes(new BitSet()),
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
                "DataHandlingMessage1(messageType=OnLineTime, kindOfTime=Start, timeType=Empty, usedLanes=UsedLanes(lanes=[false, false, false, false, false, false, false, false, false, false]), lapCount=1, event=2, heat=3, rank=4, rankInfo=Normal)",
                new DataHandlingMessage1(MessageType.OnLineTime,
                                         KindOfTime.Start,
                                         TimeType.Empty,
                                         new UsedLanes(new BitSet()),
                                         (byte) 1,
                                         (short) 2,
                                         (byte) 3,
                                         (byte) 4,
                                         RankInfo.Normal).toString());
    }
}
