package org.lisasp.alphatimer.refinedmessages;

import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedStartMessage;

import java.util.function.Consumer;

public class StartParser implements Parser {

    private EnumUtils utils = new EnumUtils();

    @Override
    public void accept(DataHandlingMessage message, Consumer<RefinedMessage> resultCollector) {
        if ((message.getMessageType() == MessageType.CurrentRaceResults || message.getMessageType() == MessageType.PreviousRaceResults || message.getMessageType() == MessageType.OnLineTime) && message.getKindOfTime() == KindOfTime.Start) {
            if (isValid(message)) {
                resultCollector.accept(new StartMessage(message.getTimestamp(),
                                                        message.getCompetition(),
                                                        message.getEvent(),
                                                        message.getHeat(),
                                                        utils.convertMessageType(message.getMessageType()),
                                                        message.getLapCount(),
                                                        message.getLane(),
                                                        message.getTimeInMillis(),
                                                        utils.convertTimeType(message.getTimeType())));
            } else {
                resultCollector.accept(new DroppedStartMessage(message));
            }
        }
    }

    private boolean isValid(DataHandlingMessage message) {
        return message.getCurrentLap() == 0 &&
                // message.getLapCount() > 0 &&
                // message.getLane() > 0 &&
                message.getRank() == 0 &&
                message.getRankInfo() == RankInfo.Normal &&
                message.getTimeMarker() == TimeMarker.Empty &&
                // message.getTimeInfo() == TimeInfo.Normal &&
                (message.getTimeType() == TimeType.Empty || message.getTimeType() == TimeType.CorrectedTime || message.getTimeType() == TimeType.ManualTime);
    }
}
