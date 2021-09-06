package org.lisasp.alphatimer.api.protocol.events.dropped;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.lisasp.alphatimer.api.protocol.data.Bytes;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class DataHandlingMessage2DroppedEvent implements InputDataDroppedEvent {
    private final LocalDateTime timestamp;
    private final String competition;
    private final String message;
    private final Bytes data;

    public DataHandlingMessage2DroppedEvent(LocalDateTime timestamp, String competition, String message, byte[] data) {
        this(timestamp, competition, message, new Bytes(data));
    }
}
