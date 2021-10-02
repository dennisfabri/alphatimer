package org.lisasp.alphatimer.protocol;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.api.protocol.BytesInputEventListener;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.basics.notification.Notifier;

import java.util.Optional;
import java.util.function.Consumer;

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
