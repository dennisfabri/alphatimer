package de.dennisfabri.alphatimer.api.protocol.events.dropped;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DataHandlingMessage1DroppedEvent implements InputDataDroppedEvent {

    private final String message;
    private final byte[] data;
}
