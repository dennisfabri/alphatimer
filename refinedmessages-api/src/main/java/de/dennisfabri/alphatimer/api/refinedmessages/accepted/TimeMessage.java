package de.dennisfabri.alphatimer.api.refinedmessages.accepted;

import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.enums.RefinedKindOfTime;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.enums.RefinedTimeType;
import lombok.Value;

@Value
public class TimeMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final RefinedMessageType messageType;
    private final RefinedKindOfTime kindOfTime;
    private final byte lane;
    private final byte currentLap;
    private final byte lapCount;
    private final byte rank;
    private final int timeOfDayInMillis;
    private final RefinedTimeType timeType;
}
