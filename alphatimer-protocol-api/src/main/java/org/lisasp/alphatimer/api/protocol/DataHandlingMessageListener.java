package org.lisasp.alphatimer.api.protocol;

import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;

import java.util.function.Consumer;

public interface DataHandlingMessageListener extends Consumer<DataHandlingMessage> {
}
