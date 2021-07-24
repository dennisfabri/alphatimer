package org.lisasp.alphatimer.refinedmessages;

import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.DidNotFinishMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.UsedLanesMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedDidNotFinishMessage;

import java.util.function.Consumer;

public class UsedLanesParser implements Parser {
    @Override
    public void accept(DataHandlingMessage message, Consumer<RefinedMessage> resultCollector) {
        if (message.getMessageType() == MessageType.ReadyToStart || message.getMessageType() == MessageType.OnLineTime) {
            if (isValid(message)) {
                resultCollector.accept(new UsedLanesMessage(message.getTimestamp(),
                                                            message.getCompetition(),
                                                            message.getEvent(),
                                                            message.getHeat(),
                                                            message.getUsedLanes().toValue()
                ));
            }
        }
    }

    private boolean isValid(DataHandlingMessage message) {
        return true;
    }
}
