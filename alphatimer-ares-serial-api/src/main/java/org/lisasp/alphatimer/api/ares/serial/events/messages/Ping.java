package org.lisasp.alphatimer.api.ares.serial.events.messages;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.lisasp.alphatimer.api.ares.serial.data.Bytes;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class Ping implements Message {
    private final LocalDateTime timestamp;
    private final String competition;
    private final Bytes data;

    public Ping(LocalDateTime timestamp, String competition, byte[] data) {
        this(timestamp, competition, new Bytes(data));
    }
}
