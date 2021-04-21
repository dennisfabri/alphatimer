package de.dennisfabri.alphatimer.api.events.dropped;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UnknownMessageDroppedEvent implements InputDataDroppedEvent {

    private final byte[] data;
}
