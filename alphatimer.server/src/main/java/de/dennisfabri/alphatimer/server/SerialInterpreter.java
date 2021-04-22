package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.collector.DataHandlingMessageAggregator;
import de.dennisfabri.alphatimer.collector.InputCollector;
import de.dennisfabri.alphatimer.legacy.LegacyTimeStorage;
import de.dennisfabri.alphatimer.legacy.LegacyXStreamUtil;
import de.dennisfabri.alphatimer.messagesstorage.Messages;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import de.dennisfabri.alphatimer.serial.SerialPortReader;
import de.dennisfabri.alphatimer.storage.DateFacade;
import de.dennisfabri.alphatimer.storage.Storage;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.TooManyListenersException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SerialInterpreter {

    private final Messages messages;
    private final SerialConnectionBuilder serialConnectionBuilder;
    private final ConfigurationValues config;
    private final Storage storage;
    private final LegacyTimeStorage legacy;
    private final InputCollector alphaTranslator;
    private final DateFacade dates;

    private String competitionKey;

    private SerialPortReader reader;

    public void start() {
        try {
            initializeCompetitionKey();
            readStoredData();
            initializePipeline();
            initializeSerialReader();
        } catch (NoSuchPortException nsp) {
            log.error("No port with specified name present.");
        } catch (PortInUseException nsp) {
            log.error("Specified port is already in use.");
        } catch (UnsupportedCommOperationException uco) {
            log.error("Unknown communication error occurred.", uco);
        }
    }

    private void initializeCompetitionKey() {
        this.competitionKey = config.getCompetitionKey();
        if (competitionKey == null || competitionKey.trim().isBlank()) {
            competitionKey = dates.today().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        log.info("Using competition key: {}", competitionKey);
    }

    private void readStoredData() {
        preload(legacy, storage);
    }

    private static void preload(LegacyTimeStorage legacy, Storage storage) {
        try (final InputCollector preloadAlphaTranslator = new InputCollector()) {
            DataHandlingMessageAggregator preloadAggregator = new DataHandlingMessageAggregator();
            preloadAggregator.register(e -> legacy.notify(e));
            preloadAlphaTranslator.register(e -> preloadAggregator.notify(e));
            for (byte b : storage.read()) {
                preloadAlphaTranslator.put(b);
            }
        } catch (IOException io) {
            log.warn("Preload not executed/finished", io);
        }
    }

    private void initializePipeline() {
        DataHandlingMessageAggregator aggregator = new DataHandlingMessageAggregator();
        aggregator.register(event -> {
            log.info("Received message: '{}'", event);
            legacy.notify(event);
            if (event instanceof DataHandlingMessage) {
                log.info("messages.put({})", event);
                messages.put((DataHandlingMessage) event, competitionKey);
            }
        });
        alphaTranslator.register(event -> {
            log.info("Received message: '{}'", event);
            aggregator.notify(event);
        });
    }

    private void initializeSerialReader() throws
                                          NoSuchPortException,
                                          PortInUseException,
                                          UnsupportedCommOperationException {
        try {
            String port = config.getSerialPort();
            if (hasNoValue(port)) {
                port = serialConnectionBuilder.autoconfigurePort();
            }

            log.info("Connecting to port: {}", port);
            reader = serialConnectionBuilder.configure(port, config.getSerialConfigurationObject()).buildReader(e -> {
                try {
                    storage.write(e);
                } catch (IOException ex) {
                    log.warn("Could not save data.", ex);
                }
                alphaTranslator.put(e);
            });
        } catch (TooManyListenersException tml) {
            log.warn("Exactly one listener is registered during startup (This exception should not occur).", tml);
        }
    }

    private boolean hasNoValue(String port) {
        return port == null || port.trim().equals("");
    }

    public String getLegacyData() {
        return LegacyXStreamUtil.getXStream().toXML(legacy.getHeats());
    }

    @PreDestroy
    public void onDestroy() {
        if (reader != null) {
            reader.close();
        }
        alphaTranslator.close();
    }
}
