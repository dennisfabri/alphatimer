package de.dennisfabri.alphatimer.api.refinedmessages;

import lombok.Value;

@Value
public class OfficialEndMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
}
