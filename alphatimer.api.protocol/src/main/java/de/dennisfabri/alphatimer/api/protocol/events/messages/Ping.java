package de.dennisfabri.alphatimer.api.protocol.events.messages;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Ping implements Message {
    private final byte[] data;
}
