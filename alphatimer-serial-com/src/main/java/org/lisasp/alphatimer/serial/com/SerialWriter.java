package org.lisasp.alphatimer.serial.com;

import gnu.io.*;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.serial.SerialPortWriter;
import org.lisasp.alphatimer.api.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.api.serial.exceptions.PortAccessException;

import java.io.IOException;

@Slf4j
class SerialWriter implements SerialPortWriter {
    private final SerialPort serialPort;

    public SerialWriter(String port, SerialConfiguration config)
            throws PortAccessException {
        try {
            log.info("Starting writer at port {} with {}", port, config);

            CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(port);
            serialPort = portId.open("PortReader", 100);

            serialPort.setSerialPortParams(config.getBaud(),
                                           config.getDatabits().getValue(),
                                           config.getStopbits().getValue(),
                                           config.getParity().getValue());
        } catch (NoSuchPortException | PortInUseException |
                UnsupportedCommOperationException e) {
            throw new PortAccessException(port, e);
        }
    }

    public void write(byte b) throws IOException {
        serialPort.getOutputStream().write(b);
    }

    @Override
    public void close() {
        serialPort.removeEventListener();
        serialPort.close();
    }
}
