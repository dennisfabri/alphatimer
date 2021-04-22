package de.dennisfabri.alphatimer.api.protocol.events.dropped;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UnknownMessageDroppedEventTest {

    @Test
    void equalsTest() {
        UnknownMessageDroppedEvent value1 = new UnknownMessageDroppedEvent(new byte[]{0x34, 0x32});
        UnknownMessageDroppedEvent value2 = new UnknownMessageDroppedEvent(new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        UnknownMessageDroppedEvent value1 = new UnknownMessageDroppedEvent(new byte[]{0x34, 0x32});
        UnknownMessageDroppedEvent value2 = new UnknownMessageDroppedEvent(new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "UnknownMessageDroppedEvent(data=[52, 50])",
                new UnknownMessageDroppedEvent(new byte[]{0x34, 0x32}).toString());
    }
}
