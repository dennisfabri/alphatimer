package org.lisasp.alphatimer.refinedmessages;

import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.*;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedOfficialEndMessage;

import java.util.function.Consumer;

public class OfficialEndParser implements Parser {
    @Override
    public void accept(DataHandlingMessage message, Consumer<RefinedMessage> resultCollector) {
        if (message.getMessageType() == MessageType.OfficialEnd) {
            if (isValid(message)) {
                resultCollector.accept(new OfficialEndMessage(
                        message.getTimestamp(),
                        message.getCompetition(),
                        message.getEvent(),
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
