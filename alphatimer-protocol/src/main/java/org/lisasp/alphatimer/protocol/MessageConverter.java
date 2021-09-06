package org.lisasp.alphatimer.protocol;

import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.messaging.Notifier;
import org.lisasp.alphatimer.protocol.parser.Parser;

import java.util.function.Consumer;

public class MessageConverter implements DataInputEventListener {

    private Notifier<DataInputEvent> notifier = new Notifier<>();
    private Parser parser = new Parser();

    @Override
    public void accept(DataInputEvent dataInputEvent) {
        if (dataInputEvent instanceof BytesInputEvent) {
            BytesInputEvent bytesInputEvent = (BytesInputEvent) dataInputEvent;
            notifier.accept(parser.parse(bytesInputEvent));
        } else {
            notifier.accept(dataInputEvent);
        }
    }

    public void register(Consumer<DataInputEvent> listener) {
        notifier.register(listener);
    }
}
