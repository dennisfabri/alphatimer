package de.dennisfabri.alphatimer.api.refinedmessages.dropped;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.*;
import de.dennisfabri.alphatimer.api.protocol.events.messages.values.UsedLanes;
import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import lombok.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class DroppedRefinedMessage implements RefinedMessage {

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

    public DroppedRefinedMessage(DataHandlingMessage message) {
        this(message.getMessageType(),
             message.getKindOfTime(),
             message.getTimeType(),
             message.getUsedLanes(),
             message.getLapCount(),
             message.getEvent(),
             message.getHeat(),
             message.getRank(),
             message.getRankInfo(),
             message.getLane(),
             message.getCurrentLap(),
             message.getTimeInMillis(),
             message.getTimeInfo(),
             message.getTimeMarker());
    }
}
