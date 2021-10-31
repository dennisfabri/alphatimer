package org.lisasp.alphatimer.serialportlistener;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialPortReader;
import org.lisasp.alphatimer.serial.Storage;
import org.lisasp.alphatimer.serial.exceptions.NoPortsFoundException;
import org.lisasp.alphatimer.serialportlistener.mq.Sender;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SerialInterpreter {

    private final SerialConnectionBuilder serialConnectionBuilder;
    private final ConfigurationValues config;
    private final Storage storage;
    private final InputCollector inputCollector;
    private final Sender sender;

    private SerialPortReader reader;

    public void start() {
        try {
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

    private void initializePipeline() {
        inputCollector.register(event -> {
            sender.send(event);
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

    @PreDestroy
    public void onDestroy() {
        if (reader != null) {
            reader.close();
        }
        inputCollector.close();
    }
}
