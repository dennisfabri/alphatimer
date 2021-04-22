package de.dennisfabri.alphatimer.api.protocol.events.messages;

import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeInfo;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeMarker;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class DataHandlingMessage2 implements Message {
    private final byte lane;
    private final byte currentLap;
    private final int timeInMillis;
    private final TimeInfo timeInfo;
    private final TimeMarker timeMarker;
}
