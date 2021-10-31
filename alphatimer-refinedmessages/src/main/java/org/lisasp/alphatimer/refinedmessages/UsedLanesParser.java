package org.lisasp.alphatimer.refinedmessages;

import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.MessageType;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.UsedLanesMessage;

import java.util.function.Consumer;

public class UsedLanesParser implements Parser {
    @Override
    public void accept(DataHandlingMessage message, Consumer<RefinedMessage> resultCollector) {
        if (message.getMessageType() == MessageType.ReadyToStart || message.getMessageType() == MessageType.OnLineTime) {
            resultCollector.accept(new UsedLanesMessage(message.getTimestamp(),
                                                        message.getCompetition(),
                                                        message.getEvent(),
                                                        message.getHeat(),
                                                        message.getUsedLanes().toValue()
            ));
        }
    }
}
