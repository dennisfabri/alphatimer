package de.dennisfabri.alphatimer.api.protocol.events.dropped;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UnstructuredMessageDroppedEventTest {

    @Test
    void equalsTest() {
        UnstructuredInputDroppedEvent value1 = new UnstructuredInputDroppedEvent(new byte[]{0x34, 0x32});
        UnstructuredInputDroppedEvent value2 = new UnstructuredInputDroppedEvent(new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        UnstructuredInputDroppedEvent value1 = new UnstructuredInputDroppedEvent(new byte[]{0x34, 0x32});
        UnstructuredInputDroppedEvent value2 = new UnstructuredInputDroppedEvent(new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "UnstructuredInputDroppedEvent(data=Bytes([52, 50]))",
                new UnstructuredInputDroppedEvent(new byte[]{0x34, 0x32}).toString());
    }
}
