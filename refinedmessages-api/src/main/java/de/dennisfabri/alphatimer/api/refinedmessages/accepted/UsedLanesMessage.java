package de.dennisfabri.alphatimer.api.refinedmessages.accepted;

import de.dennisfabri.alphatimer.api.protocol.events.messages.values.UsedLanes;
import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import lombok.Value;

@Value
public class UsedLanesMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final UsedLanes usedLanes;
}
