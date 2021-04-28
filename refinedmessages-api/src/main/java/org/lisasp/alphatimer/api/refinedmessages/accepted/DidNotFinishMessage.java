package org.lisasp.alphatimer.api.refinedmessages.accepted;

import lombok.Value;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;

@Value
public class DidNotFinishMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final byte lane;
}
