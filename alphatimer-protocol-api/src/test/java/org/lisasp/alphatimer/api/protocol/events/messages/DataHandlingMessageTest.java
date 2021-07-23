package org.lisasp.alphatimer.api.protocol.events.messages;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessageTest {
    @Test
    void equalsTest() {
        DataHandlingMessage value1 = new DataHandlingMessage("1", "2",
                                                             LocalDateTime.of(2021, 6, 1, 10, 0),
                                                             "TestWK",
                                                             MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.Empty,
                                                             new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
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
        DataHandlingMessage value2 = new DataHandlingMessage("1", "2",
                                                             LocalDateTime.of(2021, 6, 1, 10, 0),
                                                             "TestWK",
                                                             MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.Empty,
                                                             new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
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
        DataHandlingMessage value1 = new DataHandlingMessage("1", "2",
                                                             LocalDateTime.of(2021, 6, 1, 10, 0),
                                                             "TestWK",
                                                             MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.Empty,
                                                             new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
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
        DataHandlingMessage value2 = new DataHandlingMessage("1", "2",
                                                             LocalDateTime.of(2021, 6, 1, 10, 0),
                                                             "TestWK",
                                                             MessageType.OnLineTime,
                                                             KindOfTime.Start,
                                                             TimeType.PlatformTimeAfterTouchpadTime,
                                                             new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
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
                "DataHandlingMessage(originalText1=1, originalText2=2, timestamp=2021-06-01T10:00, competition=TestWK, messageType=OnLineTime, kindOfTime=Start, timeType=Empty, usedLanes=UsedLanes(1100000000), lapCount=1, event=2, heat=3, rank=4, rankInfo=Normal, lane=5, currentLap=6, timeInMillis=456123, timeInfo=Normal, timeMarker=Empty)",
                new DataHandlingMessage("1", "2",
                                        LocalDateTime.of(2021, 6, 1, 10, 0),
                                        "TestWK",
                                        MessageType.OnLineTime,
                                        KindOfTime.Start,
                                        TimeType.Empty,
                                        new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
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
