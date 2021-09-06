package org.lisasp.alphatimer.test.api.protocol.events.dropped;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UnstructuredMessageDroppedEventTest {

    private final static String competition = "TestWK";
    private final static LocalDateTime timestamp = LocalDateTime.of(2021, 6, 21, 14, 53, 23);

    @Test
    void equalsTest() {
        UnstructuredInputDroppedEvent value1 = new UnstructuredInputDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32});
        UnstructuredInputDroppedEvent value2 = new UnstructuredInputDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32});
        assertEquals(value1, value2);
    }

    @Test
    void notEqualsTest() {
        UnstructuredInputDroppedEvent value1 = new UnstructuredInputDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32});
        UnstructuredInputDroppedEvent value2 = new UnstructuredInputDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x33});
        assertNotEquals(value1, value2);
    }

    @Test
    void toStringTest() {
        assertEquals(
                "UnstructuredInputDroppedEvent(timestamp=2021-06-21T14:53:23, competition=TestWK, data=Bytes([52, 50]))",
                new UnstructuredInputDroppedEvent(timestamp, competition,  new byte[]{0x34, 0x32}).toString());
    }
}
