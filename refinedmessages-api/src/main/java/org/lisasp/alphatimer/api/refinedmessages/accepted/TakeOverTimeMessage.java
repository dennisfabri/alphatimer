package org.lisasp.alphatimer.api.refinedmessages.accepted;

import lombok.Value;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeMarker;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeType;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;

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
