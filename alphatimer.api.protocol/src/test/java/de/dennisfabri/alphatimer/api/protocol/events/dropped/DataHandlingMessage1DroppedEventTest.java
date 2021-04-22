package de.dennisfabri.alphatimer.api.protocol.events.dropped;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataHandlingMessage1DroppedEventTest {

    @Test
    void equalsTest() {
        DataHandlingMessage1DroppedEvent value1 = new DataHandlingMessage1DroppedEvent("Equals",
                                                                                       new byte[]{0x34, 0x32});
        DataHandlingMessage1DroppedEvent value2 = new DataHandlingMessage1DroppedEvent("Equals",
                                                                                       new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        DataHandlingMessage1DroppedEvent value1 = new DataHandlingMessage1DroppedEvent("NotEquals",
                                                                                       new byte[]{0x34, 0x32});
        DataHandlingMessage1DroppedEvent value2 = new DataHandlingMessage1DroppedEvent("NotEquals",
                                                                                       new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "DataHandlingMessage1DroppedEvent(message=Test, data=[52, 50])",
                new DataHandlingMessage1DroppedEvent("Test", new byte[]{0x34, 0x32}).toString());
    }
}
