package org.lisasp.alphatimer.api.ares.serial;

import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;

import java.util.function.Consumer;

public interface DataHandlingMessageListener extends Consumer<DataHandlingMessage> {
}
