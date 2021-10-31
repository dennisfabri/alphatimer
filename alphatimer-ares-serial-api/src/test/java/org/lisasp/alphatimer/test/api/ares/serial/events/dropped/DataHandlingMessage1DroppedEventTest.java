package org.lisasp.alphatimer.test.api.ares.serial.events.dropped;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.events.dropped.DataHandlingMessage1DroppedEvent;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessage1DroppedEventTest {

    private final static String competition = "TestWK";
    private final static LocalDateTime timestamp = LocalDateTime.of(2021, 6, 21, 14, 53, 23);

    @Test
    void equalsTest() {
        DataHandlingMessage1DroppedEvent value1 = new DataHandlingMessage1DroppedEvent(timestamp, competition,  "Equals",
                                                                                       new byte[]{0x34, 0x32});
        DataHandlingMessage1DroppedEvent value2 = new DataHandlingMessage1DroppedEvent(timestamp, competition,  "Equals",
                                                                                       new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage1DroppedEvent value1 = new DataHandlingMessage1DroppedEvent(timestamp, competition,  "NotEquals",
                                                                                       new byte[]{0x34, 0x32});
        DataHandlingMessage1DroppedEvent value2 = new DataHandlingMessage1DroppedEvent(timestamp, competition,  "NotEquals",
                                                                                       new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "DataHandlingMessage1DroppedEvent(timestamp=2021-06-21T14:53:23, competition=TestWK, message=Test, data=Bytes([52, 50]))",
                new DataHandlingMessage1DroppedEvent(timestamp, competition,  "Test", new byte[]{0x34, 0x32}).toString());
    }
}
