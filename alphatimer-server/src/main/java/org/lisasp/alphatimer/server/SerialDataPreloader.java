package org.lisasp.alphatimer.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.legacy.LegacyTimeStorage;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageAggregator;
import org.lisasp.alphatimer.storage.Storage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class SerialDataPreloader {

    private final LegacyTimeStorage legacy;
    private final Storage storage;

    void preload() {
        try (final InputCollector preloadInputCollector = new InputCollector()) {
            DataHandlingMessageAggregator preloadAggregator = new MessageAggregator("legacy");
            preloadAggregator.register(legacy);
            preloadInputCollector.register(e -> preloadAggregator.accept(e));
            for (byte b : storage.read()) {
                preloadInputCollector.accept(b);
            }
        } catch (IOException io) {
            log.warn("Preload not executed/finished", io);
        }
    }
}
