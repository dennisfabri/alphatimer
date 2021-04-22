package de.dennisfabri.alphatimer.api.protocol.events.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PingTest {
    @Test
    void equalsTest() {
        Ping value1 = new Ping(new byte[]{0x34, 0x32});
        Ping value2 = new Ping(new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        Ping value1 = new Ping(new byte[]{0x34, 0x32});
        Ping value2 = new Ping(new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "Ping(data=[52, 50])",
                new Ping(new byte[]{0x34, 0x32}).toString());
    }
}
