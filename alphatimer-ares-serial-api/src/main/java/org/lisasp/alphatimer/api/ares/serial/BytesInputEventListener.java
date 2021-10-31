package org.lisasp.alphatimer.api.ares.serial;

import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;

import java.util.function.Consumer;

public interface BytesInputEventListener extends Consumer<BytesInputEvent> {
}
