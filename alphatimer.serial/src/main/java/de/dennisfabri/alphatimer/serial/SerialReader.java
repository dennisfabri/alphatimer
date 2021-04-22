package de.dennisfabri.alphatimer.serial;

import de.dennisfabri.alphatimer.serial.configuration.SerialConfiguration;
import gnu.io.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.TooManyListenersException;

@Slf4j
class SerialReader implements SerialPortReader {

    private final SerialPort port;

    public SerialReader(String port, SerialConfiguration config, ByteListener listener)
            throws NoSuchPortException, PortInUseException, TooManyListenersException,
                   UnsupportedCommOperationException {
        log.info("Starting reader at port {} with {}", port, config);

        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(port);
        this.port = portId.open("PortReader", 100);
        this.port.addEventListener(ev -> {
            if (ev.getEventType() != SerialPortEvent.DATA_AVAILABLE) {
                log.debug("SerialPortEvent should be 1 (DATA_AVAILABLE) but was {}.", ev.getEventType());
                return;
            }
            try {
                while (SerialReader.this.port.getInputStream().available() > 0) {
                    byte[] readBuffer = new byte[1024];
                    int numBytes = SerialReader.this.port.getInputStream().read(readBuffer);
                    for (int x = 0; x < numBytes; x++) {
                        listener.notify(readBuffer[x]);
                    }
                }
            } catch (IOException ex) {
                log.debug("Exception while receiving data.", ex);
            }
        });
        this.port.notifyOnDataAvailable(true);
        this.port.setSerialPortParams(config.getBaud(),
                                      config.getDatabits().getValue(),
                                      config.getStopbits().getValue(),
                                      config.getParity().getValue());
    }

    @Override
    public void close() {
        port.removeEventListener();
        port.close();
    }
}
