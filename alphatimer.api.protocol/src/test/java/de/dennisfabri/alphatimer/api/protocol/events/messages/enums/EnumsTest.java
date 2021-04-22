package de.dennisfabri.alphatimer.api.protocol.events.messages.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumsTest {
    @Test
    void kindOfTime() {
        assertEquals('A', KindOfTime.Finish.getValue());
    }

    @Test
    void messageType() {
        assertEquals(5, MessageType.PreviousRaceResults.getValue());
    }

    @Test
    void rankInfo() {
        assertEquals('D', RankInfo.UnknownValueD.getValue());
    }

    @Test
    void timeInfo() {
        assertEquals('M', TimeInfo.Manual.getValue());
    }

    @Test
    void timeMarker() {
        assertEquals("DNS", TimeMarker.DidNotStart.getValue());
    }

    @Test
    void timeType() {
        assertEquals('I', TimeType.InsertedTime.getValue());
    }
}
