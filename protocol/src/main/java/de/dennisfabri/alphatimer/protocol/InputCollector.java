package de.dennisfabri.alphatimer.protocol;

import de.dennisfabri.alphatimer.api.protocol.ByteConsumer;
import de.dennisfabri.alphatimer.api.protocol.DataInputEventListener;
import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;
import de.dennisfabri.alphatimer.messaging.Notifier;

import java.util.Optional;

public class InputCollector implements ByteConsumer {

    private final ByteValidator validator = new ByteValidator();
    private final Notifier<DataInputEvent> notifier = new Notifier<>();
    private final MessageExtractor collector = new MessageExtractor();

    @Override
    public void accept(byte entry) {
        notify(collector.put(validator.ensureValidity(entry)));
    }

    private void notify(Optional<DataInputEvent> event) {
        event.ifPresent(e -> notifier.accept(e));
    }

    @Override
    public void register(DataInputEventListener listener) {
        notifier.register(listener);
    }

    @Override
    public void close() {
        notify(collector.shutdown());
    }
}
