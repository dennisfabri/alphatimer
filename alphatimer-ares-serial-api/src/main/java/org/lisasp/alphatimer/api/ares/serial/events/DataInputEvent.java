package org.lisasp.alphatimer.api.ares.serial.events;

import java.time.LocalDateTime;

public interface DataInputEvent {
    String getCompetition();
    LocalDateTime getTimestamp();
}
