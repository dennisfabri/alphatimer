package org.lisasp.alphatimer.api.protocol.events;

import java.time.LocalDateTime;

public interface DataInputEvent {
    String getCompetition();
    LocalDateTime getTimestamp();
}
