package org.lisasp.alphatimer.api.protocol.events.messages;

import lombok.Value;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;

import java.time.LocalDateTime;

@Value
public class DataHandlingMessage implements Message {
    private final LocalDateTime timestamp;
    private final String competition;
    private final String originalText1;
    private final String originalText2;
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
