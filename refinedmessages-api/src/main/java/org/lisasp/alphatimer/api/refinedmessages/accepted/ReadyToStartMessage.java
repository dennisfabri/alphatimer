package org.lisasp.alphatimer.api.refinedmessages.accepted;

import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import lombok.Value;

@Value
public class ReadyToStartMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final byte lapCount;
}
