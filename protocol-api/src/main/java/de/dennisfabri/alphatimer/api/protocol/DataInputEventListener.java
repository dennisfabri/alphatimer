package de.dennisfabri.alphatimer.api.protocol;

import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;

import java.util.function.Consumer;

public interface DataInputEventListener extends Consumer<DataInputEvent> {
}
