package org.lisasp.alphatimer.api.refinedmessages.accepted;

import lombok.Value;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;

@Value
public class UsedLanesMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final UsedLanes usedLanes;
}
