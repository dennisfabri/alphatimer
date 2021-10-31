package org.lisasp.alphatimer.ares.serial;

import org.lisasp.alphatimer.api.ares.serial.BytesInputEventListener;
import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;
import org.lisasp.alphatimer.api.ares.serial.events.DataInputEvent;
import org.lisasp.alphatimer.ares.serial.parser.Parser;
import org.lisasp.basics.notification.Notifier;

import java.util.function.Consumer;

public class MessageConverter implements BytesInputEventListener {

    private final Notifier<DataInputEvent> notifier = new Notifier<>();
    private final Parser parser = new Parser();

    @Override
    public void accept(BytesInputEvent bytesInputEvent) {
        notifier.accept(parser.parse(bytesInputEvent));
    }

    public void register(Consumer<DataInputEvent> listener) {
        notifier.register(listener);
    }
}
