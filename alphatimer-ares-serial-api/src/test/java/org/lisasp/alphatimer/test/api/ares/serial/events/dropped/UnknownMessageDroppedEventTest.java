package org.lisasp.alphatimer.test.api.ares.serial.events.dropped;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.events.dropped.UnknownMessageDroppedEvent;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UnknownMessageDroppedEventTest {

    private final static String competition = "TestWK";
    private final static LocalDateTime timestamp = LocalDateTime.of(2021, 6, 21, 14, 53, 23);

    @Test
    void equalsTest() {
        UnknownMessageDroppedEvent value1 = new UnknownMessageDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32});
        UnknownMessageDroppedEvent value2 = new UnknownMessageDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        UnknownMessageDroppedEvent value1 = new UnknownMessageDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32});
        UnknownMessageDroppedEvent value2 = new UnknownMessageDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "UnknownMessageDroppedEvent(timestamp=2021-06-21T14:53:23, competition=TestWK, data=Bytes([52, 50]))",
                new UnknownMessageDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32}).toString());
    }
}
