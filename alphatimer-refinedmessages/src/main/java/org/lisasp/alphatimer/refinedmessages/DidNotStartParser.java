package org.lisasp.alphatimer.refinedmessages;

import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.DidNotStartMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedDidNotStartMessage;

import java.util.function.Consumer;

public class DidNotStartParser implements Parser {
    @Override
    public void accept(DataHandlingMessage message, Consumer<RefinedMessage> resultCollector) {
        if (message.getMessageType() == MessageType.CurrentRaceResults && message.getTimeMarker() == TimeMarker.DidNotStart) {
            if (isValid(message)) {
                resultCollector.accept(new DidNotStartMessage(message.getTimestamp(),
                                                              message.getCompetition(),
                                                              message.getEvent(),
                                                              message.getHeat(),
                                                              message.getLane()
                ));
            } else {
                resultCollector.accept(new DroppedDidNotStartMessage(message));
            }
        }
    }

    private boolean isValid(DataHandlingMessage message) {
        return message.getCurrentLap() == 0 &&
                message.getLapCount() == 0 &&
                message.getLane() > 0 &&
                message.getRank() == 0 &&
                message.getKindOfTime() == KindOfTime.Empty &&
                message.getRankInfo() == RankInfo.Normal &&
                message.getTimeInfo() == TimeInfo.Normal &&
                message.getTimeType() == TimeType.Empty &&
                message.getTimeInMillis() == 0;
    }
}
