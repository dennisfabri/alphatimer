package org.lisasp.alphatimer.api.protocol.events.messages;

import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeInfo;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeMarker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessage2Test {
    @Test
    void equalsTest() {
        DataHandlingMessage2 value1 = new DataHandlingMessage2("2",
                                                               (byte) 5,
                                                               (byte) 6,
                                                               456123,
                                                               TimeInfo.Normal,
                                                               TimeMarker.Empty);
        DataHandlingMessage2 value2 = new DataHandlingMessage2("2",
                                                               (byte) 5,
                                                               (byte) 6,
                                                               456123,
                                                               TimeInfo.Normal,
                                                               TimeMarker.Empty);
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage2 value1 = new DataHandlingMessage2("2",
                                                               (byte) 5,
                                                               (byte) 6,
                                                               456123,
                                                               TimeInfo.Normal,
                                                               TimeMarker.Empty);
        DataHandlingMessage2 value2 = new DataHandlingMessage2("2",
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
                "DataHandlingMessage2(original=2, lane=5, currentLap=6, timeInMillis=456123, timeInfo=Normal, timeMarker=Empty)",
                new DataHandlingMessage2("2",
                                         (byte) 5,
                                         (byte) 6,
                                         456123,
                                         TimeInfo.Normal,
                                         TimeMarker.Empty).toString());
    }
}
