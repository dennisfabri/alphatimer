package de.dennisfabri.alphatimer.refinedmessages;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;

import java.util.function.Consumer;

interface Parser {
    void accept(DataHandlingMessage dataHandlingMessage, Consumer<RefinedMessage> resultCollector);
}
