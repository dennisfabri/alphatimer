package org.lisasp.alphatimer.api.protocol.events.messages;

import lombok.Value;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.KindOfTime;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.MessageType;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.RankInfo;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeType;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;

import java.time.LocalDateTime;

@Value
public class DataHandlingMessage1 implements Message {
    private final LocalDateTime timestamp;
    private final String competition;
    private final String original;
    private final MessageType messageType;
    private final KindOfTime kindOfTime;
    private final TimeType timeType;
    private final UsedLanes usedLanes;
    private final byte lapCount;
    private final short event;
    private final byte heat;
    private final byte rank;
    private final RankInfo rankInfo;

}
