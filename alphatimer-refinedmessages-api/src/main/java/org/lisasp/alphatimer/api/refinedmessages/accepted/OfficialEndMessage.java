package org.lisasp.alphatimer.api.refinedmessages.accepted;

import lombok.Value;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;

@Value
public class OfficialEndMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final byte lapCount;
}
