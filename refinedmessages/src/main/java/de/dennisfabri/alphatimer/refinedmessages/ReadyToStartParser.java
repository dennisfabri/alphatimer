package de.dennisfabri.alphatimer.refinedmessages;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.*;
import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.ReadyToStartMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.dropped.DroppedReadyToStartMessage;

import java.util.function.Consumer;

public class ReadyToStartParser implements Parser {
    @Override
    public void accept(DataHandlingMessage message, Consumer<RefinedMessage> resultCollector) {
        if (message.getMessageType() == MessageType.ReadyToStart) {
            if (isValid(message)) {
                resultCollector.accept(new ReadyToStartMessage(message.getEvent(),
                                                               message.getHeat(),
                                                               message.getLapCount()));
            } else {
                resultCollector.accept(new DroppedReadyToStartMessage(message));
            }
        }
    }

    private boolean isValid(DataHandlingMessage message) {
        return message.getCurrentLap() == 0 &&
                message.getKindOfTime() == KindOfTime.Empty &&
                message.getLane() == 0 &&
                message.getRank() == 0 &&
                message.getRankInfo() == RankInfo.Normal &&
                message.getTimeMarker() == TimeMarker.Empty &&
                message.getTimeInfo() == TimeInfo.Normal &&
                message.getTimeType() == TimeType.Empty &&
                message.getTimeInMillis() == 0;
    }
}
