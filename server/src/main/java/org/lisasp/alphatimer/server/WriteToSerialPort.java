package org.lisasp.alphatimer.server;

import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialPortWriter;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.serial.exceptions.NoPortsFoundException;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class WriteToSerialPort {

    void run(String filename, String port, String config, SerialConnectionBuilder serialConnectionBuilder)
            throws UnsupportedCommOperationException, NoSuchPortException, PortInUseException,
                   NoPortsFoundException, IOException {
        if (isEmpty(filename)) {
            log.error("Filename not specified.");
            return;
        }
        if (isEmpty(port)) {
            port = serialConnectionBuilder.autoconfigurePort();
        }
        SerialConfiguration serialConfiguration = new ConfigurationValues().getSerialConfigurationObject(config);

        log.info("Writing content of file {} to port {} with settings {}.", filename, port, serialConfiguration);
        try (SerialPortWriter writer = serialConnectionBuilder.configure(port, serialConfiguration).buildWriter()) {
            for (byte b : Files.readAllBytes(Path.of(filename))) {
                writer.write(b);
            }
        }
        log.info("Finished");
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
