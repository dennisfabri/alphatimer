package org.lisasp.alphatimer.server;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageRepository;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.alphatimer.legacy.LegacyTimeStorage;
import org.lisasp.alphatimer.legacy.LegacyXStreamUtil;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageAggregator;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialPortReader;
import org.lisasp.alphatimer.serial.exceptions.NoPortsFoundException;
import org.lisasp.alphatimer.server.mq.Sender;
import org.lisasp.alphatimer.storage.Storage;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SerialInterpreter {

    private final DataHandlingMessageRepository messages;
    private final SerialConnectionBuilder serialConnectionBuilder;
    private final ConfigurationValues config;
    private final Storage storage;
    private final LegacyTimeStorage legacy;
    private final InputCollector inputCollector;
    private final DataHandlingMessageRefiner messageRefiner;
    private final Sender sender;

    private String competitionKey;

    private SerialPortReader reader;

    public void start() {
        try {
            initializeCompetitionKey();
            initializePipeline();
            initializeSerialReader();
        } catch (NoPortsFoundException | NoSuchPortException nsp) {
            log.error("No port with specified name present.");
        } catch (PortInUseException nsp) {
            log.error("Specified port is already in use.");
        } catch (UnsupportedCommOperationException uco) {
            log.error("Unknown communication error occurred.", uco);
        }
    }

    private void initializeCompetitionKey() {
        this.competitionKey = config.getActualCompetitionKey();
        log.info("Using competition key: {}", competitionKey);
    }

    private void initializePipeline() {
        messageRefiner.register(m -> sender.send(m));

        DataHandlingMessageAggregator aggregator = new MessageAggregator(competitionKey);
        aggregator.register(legacy);
        aggregator.register(e -> messages.put(e));
        aggregator.register(messageRefiner);

        inputCollector.register(event -> {
            if (!(event instanceof Ping)) {
                log.info("Received message: '{}'", event);
            }
            aggregator.accept(event);
        });
    }

    private void initializeSerialReader() throws
                                          NoSuchPortException,
                                          PortInUseException,
                                          NoPortsFoundException,
                                          UnsupportedCommOperationException {
        String port = config.getSerialPort();
        if (hasNoValue(port)) {
            port = serialConnectionBuilder.autoconfigurePort();
        }

        log.info("Connecting to port: {}", port);
        reader = serialConnectionBuilder.configure(port, config.getSerialConfigurationObject()).buildReader().register(e -> {
            try {
                storage.write(e);
            } catch (IOException ex) {
                log.warn("Could not save data.", ex);
            }
        }).register(inputCollector);
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
        inputCollector.close();
    }
}
