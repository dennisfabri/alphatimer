package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.DataTranslator;
import de.dennisfabri.alphatimer.api.events.DataInputEvent;

import java.util.Optional;

public class AlphaTranslator implements DataTranslator {

    private final ByteValidator validator = new ByteValidator();
    private final Notifier notifier = new Notifier();
    private final MessageExtractor collector = new MessageExtractor();

    @Override
    public void put(byte entry) {
        notify(collector.put(validator.ensureValidity(entry)));
    }

    private void notify(Optional<DataInputEvent> event) {
        event.ifPresent(e -> notifier.notify(e));
    }

    @Override
    public void register(DataListener listener) {
        notifier.register(listener);
    }

    @Override
    public void close() {
        notify(collector.shutdown());
    }
}
