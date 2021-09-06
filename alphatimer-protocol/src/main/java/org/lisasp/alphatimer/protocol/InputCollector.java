package org.lisasp.alphatimer.protocol;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.api.protocol.ByteInputConverter;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.messaging.Notifier;

import java.util.Optional;

@RequiredArgsConstructor
public class InputCollector implements ByteInputConverter {

    private final String competition;
    private final DateTimeFacade dateTime;

    private final ByteValidator validator = new ByteValidator();
    private final Notifier<DataInputEvent> notifier = new Notifier<>();
    private final MessageExtractor collector = new MessageExtractor();

    @Override
    public void accept(byte entry) {
        notify(createEvent(collector.put(validator.ensureValidity(entry))));
    }

    private void notify(Optional<BytesInputEvent> event) {
        event.ifPresent(e -> notifier.accept(e));
    }

    @Override
    public void register(DataInputEventListener listener) {
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
