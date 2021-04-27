package org.lisasp.alphatimer.refinedmessages;

import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;

import java.util.function.Consumer;

interface Parser {
    void accept(DataHandlingMessage dataHandlingMessage, Consumer<RefinedMessage> resultCollector);
}
