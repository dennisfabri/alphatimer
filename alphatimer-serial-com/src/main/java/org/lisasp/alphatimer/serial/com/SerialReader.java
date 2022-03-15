package org.lisasp.alphatimer.serial.com;

import gnu.io.*;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.api.serial.exceptions.PortAccessException;
import org.lisasp.basics.notification.primitive.ByteListener;
import org.lisasp.basics.notification.primitive.ByteNotifier;

import java.io.IOException;
import java.util.TooManyListenersException;

@Slf4j
class SerialReader implements SerialPortReader {

    private final SerialPort port;

    private final ByteNotifier notifier = new ByteNotifier();

    public SerialReader(String port, SerialConfiguration config)
            throws PortAccessException {
        try {
            log.info("Starting reader at port {} with {}", port, config);

            CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(port);
            this.port = portId.open("PortReader", 100);
            try {
                this.port.addEventListener(ev -> notify(ev));
            } catch (TooManyListenersException ex) {
                throw new RuntimeException("Exactly one listener has been registered.", ex);
            }
            this.port.notifyOnDataAvailable(true);
            this.port.setSerialPortParams(config.getBaud(),
                                          config.getDatabits().getValue(),
                                          config.getStopbits().getValue(),
                                          config.getParity().getValue());
        } catch (PortInUseException | UnsupportedCommOperationException | NoSuchPortException e) {
            throw new PortAccessException(port, e);
        }
    }

    private void notify(SerialPortEvent ev) {
        if (ev.getEventType() != SerialPortEvent.DATA_AVAILABLE) {
            log.debug("SerialPortEvent should be 1 (DATA_AVAILABLE) but was {}.", ev.getEventType());
            return;
        }
        try {
            while (SerialReader.this.port.getInputStream().available() > 0) {
                byte[] readBuffer = new byte[1024];
                int numBytes = SerialReader.this.port.getInputStream().read(readBuffer);
                for (int x = 0; x < numBytes; x++) {
                    notifier.accept(readBuffer[x]);
                }
            }
        } catch (IOException ex) {
            log.debug("Exception while receiving data.", ex);
        }
    }

    public SerialPortReader register(ByteListener listener) {
        notifier.register(listener);
        return this;
    }

    @Override
    public void close() {
        port.removeEventListener();
        port.close();
    }
}
