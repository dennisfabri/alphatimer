package org.lisasp.alphatimer.ares.serial;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.api.ares.serial.BytesInputEventListener;
import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.basics.notification.Notifier;

import java.util.Optional;

@RequiredArgsConstructor
public class InputCollector implements ByteInputConverter {

    private final String competition;
    private final DateTimeFacade dateTime;

    private final ByteValidator validator = new ByteValidator();
    private final Notifier<BytesInputEvent> notifier = new Notifier<>();
    private final MessageExtractor collector = new MessageExtractor();

    @Override
    public void accept(byte entry) {
        notify(createEvent(collector.put(validator.ensureValidity(entry))));
    }

    private void notify(Optional<BytesInputEvent> event) {
        event.ifPresent(e -> notifier.accept(e));
    }

    @Override
    public void register(BytesInputEventListener listener) {
        notifier.register(listener);
    }

    @Override
    public void close() {
        notify(createEvent(collector.shutdown()));
    }

    private Optional<BytesInputEvent> createEvent(Optional<byte[]> data) {
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new BytesInputEvent(dateTime.now(), competition, data.get()));
    }
}
