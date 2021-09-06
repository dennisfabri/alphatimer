package org.lisasp.alphatimer.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageRepository;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.alphatimer.legacy.LegacySerialization;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.protocol.MessageAggregator;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.server.mq.Sender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class SerialInterpreter implements Consumer<DataInputEvent> {

    private final DataHandlingMessageRepository messages;
    private final ConfigurationValues config;
    private final LegacyService legacy;
    private final MessageConverter messageConverter;
    private final DataHandlingMessageRefiner messageRefiner;
    private final Sender sender;

    @Override
    public void accept(DataInputEvent event) {
        messageConverter.accept(event);
    }

    @PostConstruct
    public void start() {
        initializePipeline();
    }

    private void initializePipeline() {
        messageRefiner.register(m -> sender.send(m));

        DataHandlingMessageAggregator aggregator = new MessageAggregator();
        aggregator.register(legacy);
        aggregator.register(e -> messages.put(e));
        aggregator.register(messageRefiner);

        messageConverter.register(event -> {
            if (!(event instanceof Ping)) {
                log.info("Received message: '{}'", event);
            }
            aggregator.accept(event);
        });
    }

    private boolean hasNoValue(String port) {
        return port == null || port.trim().equals("");
    }

    public String getLegacyData() {
        return LegacySerialization.toXML(legacy.getHeats());
    }

    @PreDestroy
    public void onDestroy() {

    }
}
