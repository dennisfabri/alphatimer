package org.lisasp.alphatimer.test.api.protocol.events.messages;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessageTest {

    private final static String competition = "TestWK";
    private final static LocalDateTime timestamp = LocalDateTime.of(2021, 6, 1, 10, 0, 0);

    @Test
    void equalsTest() {
        DataHandlingMessage value1 = new DataHandlingMessage(timestamp, competition, "1", "2",
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
        DataHandlingMessage value2 = new DataHandlingMessage(timestamp, competition,  "1", "2",
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
        DataHandlingMessage value1 = new DataHandlingMessage(timestamp, competition,  "1", "2",
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
        DataHandlingMessage value2 = new DataHandlingMessage(timestamp, competition,  "1", "2",
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
                "DataHandlingMessage(timestamp=2021-06-01T10:00, competition=TestWK, originalText1=1, originalText2=2, messageType=OnLineTime, kindOfTime=Start, timeType=Empty, usedLanes=UsedLanes(1100000000), lapCount=1, event=2, heat=3, rank=4, rankInfo=Normal, lane=5, currentLap=6, timeInMillis=456123, timeInfo=Normal, timeMarker=Empty)",
                new DataHandlingMessage(timestamp, competition,  "1", "2",
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
