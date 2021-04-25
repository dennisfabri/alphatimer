package de.dennisfabri.alphatimer.api.protocol;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;

import java.util.function.Consumer;

public interface DataHandlingMessageListener extends Consumer<DataHandlingMessage> {
}
