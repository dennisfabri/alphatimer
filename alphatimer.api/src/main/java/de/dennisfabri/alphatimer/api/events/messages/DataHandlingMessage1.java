package de.dennisfabri.alphatimer.api.events.messages;

import de.dennisfabri.alphatimer.api.events.messages.enums.KindOfTime;
import de.dennisfabri.alphatimer.api.events.messages.enums.MessageType;
import de.dennisfabri.alphatimer.api.events.messages.enums.RankInfo;
import de.dennisfabri.alphatimer.api.events.messages.enums.TimeType;
import de.dennisfabri.alphatimer.api.events.messages.values.UsedLanes;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class DataHandlingMessage1 implements Message {
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
