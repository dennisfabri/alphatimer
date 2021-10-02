package org.lisasp.alphatimer.protocol;

import org.lisasp.alphatimer.api.protocol.BytesInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.protocol.parser.Parser;
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
