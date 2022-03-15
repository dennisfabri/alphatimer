package org.lisasp.alphatimer.serialportlistener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.Storage;
import org.lisasp.alphatimer.serialportlistener.mq.Sender;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SerialInterpreter {

    // private final SerialConnectionBuilder serialConnectionBuilder;
    private final ConfigurationValues config;
    private final Storage storage;
    private final InputCollector inputCollector;
    private final Sender sender;

    private final SerialPortReader reader;

    public void start() {
            initializePipeline();
            initializeSerialReader();
    }

    private void initializePipeline() {
        inputCollector.register(event -> {
            sender.send(event);
        });
    }

    private void initializeSerialReader() {
        reader.register(e -> {
            try {
                storage.write(e);
            } catch (IOException ex) {
                log.warn("Could not save data.", ex);
            }
        }).register(inputCollector);
    }

    @PreDestroy
    public void onDestroy() {
        if (reader != null) {
            reader.close();
        }
        inputCollector.close();
    }
}
