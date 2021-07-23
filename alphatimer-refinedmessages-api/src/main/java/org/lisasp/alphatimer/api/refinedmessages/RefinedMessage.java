package org.lisasp.alphatimer.api.refinedmessages;

import java.time.LocalDateTime;

public interface RefinedMessage {

    String getCompetition();

    short getEvent();

    byte getHeat();

    LocalDateTime getTimestamp();
}
