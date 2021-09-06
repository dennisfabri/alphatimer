package org.lisasp.alphatimer.test.api.protocol.events.messages;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PingTest {

    private final static String competition = "TestWK";
    private final static LocalDateTime timestamp = LocalDateTime.of(2021, 6, 21, 14, 53, 23);

    @Test
    void equalsTest() {
        Ping value1 = new Ping(timestamp, competition,  new byte[]{0x34, 0x32});
        Ping value2 = new Ping(timestamp, competition,  new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        Ping value1 = new Ping(timestamp, competition,  new byte[]{0x34, 0x32});
        Ping value2 = new Ping(timestamp, competition,  new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "Ping(timestamp=2021-06-21T14:53:23, competition=TestWK, data=Bytes([52, 50]))",
                new Ping(timestamp, competition,  new byte[]{0x34, 0x32}).toString());
    }
}
