package org.lisasp.alphatimer.api.ares.serial;

import org.lisasp.alphatimer.api.ares.serial.events.DataInputEvent;

import java.util.function.Consumer;

public interface DataInputEventListener extends Consumer<DataInputEvent> {
}
