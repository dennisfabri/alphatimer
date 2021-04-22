package de.dennisfabri.alphatimer.api.refinedmessages;

import lombok.Value;

@Value
public class StartMessage implements RefinedMessage {
    private final short event;
    private final byte heat;
    private final int timeOfDayInMillis;
}
