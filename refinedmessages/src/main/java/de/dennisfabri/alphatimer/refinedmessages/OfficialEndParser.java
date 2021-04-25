package de.dennisfabri.alphatimer.refinedmessages;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.*;
import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.dropped.DroppedOfficialEndMessage;

import java.util.function.Consumer;

public class OfficialEndParser implements Parser {
    @Override
    public void accept(DataHandlingMessage message, Consumer<RefinedMessage> resultCollector) {
        if (message.getMessageType() == MessageType.OfficialEnd) {
            if (isValid(message)) {
                resultCollector.accept(new OfficialEndMessage(message.getEvent(),
                                                              message.getHeat(),
                                                              message.getLapCount()));
            } else {
                resultCollector.accept(new DroppedOfficialEndMessage(message));
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
