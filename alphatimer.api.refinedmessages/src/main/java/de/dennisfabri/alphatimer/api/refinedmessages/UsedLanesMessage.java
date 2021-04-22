package de.dennisfabri.alphatimer.api.refinedmessages;

import de.dennisfabri.alphatimer.api.protocol.events.messages.values.UsedLanes;
import lombok.Value;

@Value
public class UsedLanesMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final UsedLanes usedLanes;
}
