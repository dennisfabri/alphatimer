package de.dennisfabri.alphatimer.api.events.messages;

import de.dennisfabri.alphatimer.api.events.messages.enums.*;
import de.dennisfabri.alphatimer.api.events.messages.values.UsedLanes;
import de.dennisfabri.alphatimer.api.events.messages.enums.*;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessageTest {
    @Test
    void equalsTest() {
        DataHandlingMessage value1 = new DataHandlingMessage(MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.Empty,
                                                             new UsedLanes(new BitSet()),
                                                             (byte) 1,
                                                             (short) 2,
                                                             (byte) 3,
                                                             (byte) 4,
                                                             RankInfo.Normal,
                                                             (byte) 5,
                                                             (byte) 6,
                                                             456123,
                                                             TimeInfo.Normal,
                                                             TimeMarker.Empty);
        DataHandlingMessage value2 = new DataHandlingMessage(MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.Empty,
                                                             new UsedLanes(new BitSet()),
                                                             (byte) 1,
                                                             (short) 2,
                                                             (byte) 3,
                                                             (byte) 4,
                                                             RankInfo.Normal,
                                                             (byte) 5,
                                                             (byte) 6,
                                                             456123,
                                                             TimeInfo.Normal,
                                                             TimeMarker.Empty);
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage value1 = new DataHandlingMessage(MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.Empty,
                                                             new UsedLanes(new BitSet()),
                                                             (byte) 1,
                                                             (short) 2,
                                                             (byte) 3,
                                                             (byte) 4,
                                                             RankInfo.Normal,
                                                             (byte) 5,
                                                             (byte) 6,
                                                             456123,
                                                             TimeInfo.Normal,
                                                             TimeMarker.Empty);
        DataHandlingMessage value2 = new DataHandlingMessage(MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.PlatformTimeAfterTouchpadTime,
                                                             new UsedLanes(new BitSet()),
                                                             (byte) 1,
                                                             (short) 2,
                                                             (byte) 3,
                                                             (byte) 4,
                                                             RankInfo.Normal,
                                                             (byte) 5,
                                                             (byte) 6,
                                                             456123,
                                                             TimeInfo.Normal,
                                                             TimeMarker.Empty);
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "DataHandlingMessage(messageType=OnLineTime, kindOfTime=Start, timeType=Empty, usedLanes=UsedLanes(lanes=[false, false, false, false, false, false, false, false, false, false]), lapCount=1, event=2, heat=3, rank=4, rankInfo=Normal, lane=5, currentLap=6, timeInMillis=456123, timeInfo=Normal, timeMarker=Empty)",
                new DataHandlingMessage(MessageType.OnLineTime,
                                        KindOfTime.Start,
                                        TimeType.Empty,
                                        new UsedLanes(new BitSet()),
                                        (byte) 1,
                                        (short) 2,
                                        (byte) 3,
                                        (byte) 4,
                                        RankInfo.Normal,
                                        (byte) 5,
                                        (byte) 6,
                                        456123,
                                        TimeInfo.Normal,
                                        TimeMarker.Empty).toString());
    }
}
