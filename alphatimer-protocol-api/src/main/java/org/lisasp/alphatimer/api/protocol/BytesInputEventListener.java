package org.lisasp.alphatimer.api.protocol;

import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;

import java.util.function.Consumer;

public interface BytesInputEventListener extends Consumer<BytesInputEvent> {
}
