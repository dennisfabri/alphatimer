package de.dennisfabri.alphatimer.api.protocol.events.messages;

import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.*;
import de.dennisfabri.alphatimer.api.protocol.events.messages.values.UsedLanes;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class DataHandlingMessage implements Message {
    private final MessageType messageType;
    private final KindOfTime kindOfTime;
    private final TimeType timeType;
    private final UsedLanes usedLanes;
    private final byte lapCount;
    private final short event;
    private final byte heat;
    private final byte rank;
    private final RankInfo rankInfo;
    private final byte lane;
    private final byte currentLap;
    private final int timeInMillis;
    private final TimeInfo timeInfo;
    private final TimeMarker timeMarker;
}
