package de.dennisfabri.alphatimer.api.refinedmessages.accepted;

import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeMarker;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeType;
import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import lombok.Value;

@Value
public class TakeOverTimeMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final RefinedMessageType messageType;
    private final byte lane;
    private final byte currentLap;
    private final TimeMarker timeMarker;
    private final TimeType timeType;
}
