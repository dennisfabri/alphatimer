package de.dennisfabri.alphatimer.api.refinedmessages.accepted;

import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.enums.RefinedTimeType;
import lombok.Value;

@Value
public class StartMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final RefinedMessageType messageType;
    private final byte lapCount;
    private final byte lane;
    private final int timeOfDayInMillis;
    private final RefinedTimeType timeType;
}
