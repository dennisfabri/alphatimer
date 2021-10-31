package org.lisasp.alphatimer.api.ares.serial.events.messages;

import lombok.Value;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.TimeInfo;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.TimeMarker;

import java.time.LocalDateTime;

@Value
public class DataHandlingMessage2 implements Message{
    private final LocalDateTime timestamp;
    private final String competition;
    private final String original;
    private final byte lane;
    private final byte currentLap;
    private final int timeInMillis;
    private final TimeInfo timeInfo;
    private final TimeMarker timeMarker;
}
