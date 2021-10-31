package org.lisasp.alphatimer.test.api.ares.serial.events.messages;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage2;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.TimeInfo;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.TimeMarker;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessage2Test {

    private final static String competition = "TestWK";
    private final static LocalDateTime timestamp = LocalDateTime.of(2021, 6, 21, 14, 53, 23);

    @Test
    void equalsTest() {
        DataHandlingMessage2 value1 = new DataHandlingMessage2(timestamp, competition,  "2",
                                                               (byte) 5,
                                                               (byte) 6,
                                                               456123,
                                                               TimeInfo.Normal,
                                                               TimeMarker.Empty);
        DataHandlingMessage2 value2 = new DataHandlingMessage2(timestamp, competition,  "2",
                                                               (byte) 5,
                                                               (byte) 6,
                                                               456123,
                                                               TimeInfo.Normal,
                                                               TimeMarker.Empty);
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage2 value1 = new DataHandlingMessage2(timestamp, competition,  "2",
                                                               (byte) 5,
                                                               (byte) 6,
                                                               456123,
                                                               TimeInfo.Normal,
                                                               TimeMarker.Empty);
        DataHandlingMessage2 value2 = new DataHandlingMessage2(timestamp, competition,  "2",
                                                               (byte) 5,
                                                               (byte) 6,
                                                               456123,
                                                               TimeInfo.Backup,
                                                               TimeMarker.Empty);
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "DataHandlingMessage2(timestamp=2021-06-21T14:53:23, competition=TestWK, original=2, lane=5, currentLap=6, timeInMillis=456123, timeInfo=Normal, timeMarker=Empty)",
                new DataHandlingMessage2(timestamp, competition,  "2",
                                         (byte) 5,
                                         (byte) 6,
                                         456123,
                                         TimeInfo.Normal,
                                         TimeMarker.Empty).toString());
    }
}
