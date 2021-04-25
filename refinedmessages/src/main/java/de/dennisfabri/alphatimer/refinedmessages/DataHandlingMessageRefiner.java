package de.dennisfabri.alphatimer.refinedmessages;

import de.dennisfabri.alphatimer.api.protocol.DataHandlingMessageListener;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.MessageType;
import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.dropped.DroppedUnknownMessage;
import de.dennisfabri.alphatimer.messaging.CollectingConsumer;
import de.dennisfabri.alphatimer.messaging.Notifier;

import java.util.function.Consumer;

public class DataHandlingMessageRefiner implements DataHandlingMessageListener {

    private final Notifier<RefinedMessage> notifier = new Notifier<>();

    private final Parser[] parsers = new Parser[]{
            new ReadyToStartParser(),
            new OfficialEndParser(),
            new TimeParser(),
            new StartParser(),
            new DidNotStartParser(),
            new DidNotFinishParser(),
            new TakeOverTimeParser()
    };

    @Override
    public void accept(final DataHandlingMessage message) {
        parse(message).forEach(m -> notifier.accept(m));
    }

    private CollectingConsumer<RefinedMessage> parse(DataHandlingMessage message) {
        CollectingConsumer<RefinedMessage> collector = new CollectingConsumer<>();

        for (Parser parser : parsers) {
            parser.accept(message, collector);
        }

        if (collector.isEmpty()) {
            if (message.getMessageType() == MessageType.UnknownValue7) {
                collector.accept(new DroppedUnknownMessage(message));
            } else {
                collector.accept(new DroppedUnknownMessage(message));
            }
        }

        return collector;
    }

    public void register(Consumer<RefinedMessage> listener) {
        notifier.register(listener);
    }
}
