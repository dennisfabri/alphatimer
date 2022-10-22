package org.lisasp.alphatimer.serialportlistener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.Storage;
import org.lisasp.alphatimer.serialportlistener.tcp.TcpServer;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SerialInterpreter {

    private final ConfigurationValues config;
    private final Storage storage;
    private final TcpServer tcpServer;

    private final SerialPortReader reader;

    public void start() {
        initializeSerialReader();
    }

    private void initializeSerialReader() {
        reader.register(e -> {
            try {
                storage.write(e);
            } catch (IOException ex) {
                log.warn("Could not save data.", ex);
            }
        }).register(e -> tcpServer.send(e));
    }

    @PreDestroy
    public void onDestroy() {
        if (reader != null) {
            reader.close();
        }
    }
}
