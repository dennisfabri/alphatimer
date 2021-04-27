package org.lisasp.alphatimer.serial;

import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import gnu.io.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class SerialWriter implements SerialPortWriter {
    private final SerialPort serialPort;

    public SerialWriter(String port, SerialConfiguration config)
            throws NoSuchPortException, PortInUseException,
                   UnsupportedCommOperationException {
        log.info("Starting writer at port {} with {}", port, config);

        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(port);
        serialPort = portId.open("PortReader", 100);

        serialPort.setSerialPortParams(config.getBaud(),
                                       config.getDatabits().getValue(),
                                       config.getStopbits().getValue(),
                                       config.getParity().getValue());
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
