package org.lisasp.alphatimer.api.protocol.events.dropped;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessage2DroppedEventTest {

    @Test
    void equalsTest() {
        DataHandlingMessage2DroppedEvent value1 = new DataHandlingMessage2DroppedEvent("Equals",
                                                                                       new byte[]{0x34, 0x32});
        DataHandlingMessage2DroppedEvent value2 = new DataHandlingMessage2DroppedEvent("Equals",
                                                                                       new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage2DroppedEvent value1 = new DataHandlingMessage2DroppedEvent("NotEquals",
                                                                                       new byte[]{0x34, 0x32});
        DataHandlingMessage2DroppedEvent value2 = new DataHandlingMessage2DroppedEvent("NotEquals",
                                                                                       new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "DataHandlingMessage2DroppedEvent(message=Test, data=Bytes([52, 50]))",
                new DataHandlingMessage2DroppedEvent("Test", new byte[]{0x34, 0x32}).toString());
    }
}
