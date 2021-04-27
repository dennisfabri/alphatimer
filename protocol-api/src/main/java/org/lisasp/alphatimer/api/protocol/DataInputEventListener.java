package org.lisasp.alphatimer.api.protocol;

import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;

import java.util.function.Consumer;

public interface DataInputEventListener extends Consumer<DataInputEvent> {
}
