package org.lisasp.alphatimer.serial;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.messaging.ByteListener;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.serial.exceptions.NotEnoughSerialPortsException;

import java.io.IOException;
import java.util.TooManyListenersException;

@RequiredArgsConstructor
@Slf4j
public class SerialLoopTester {

    private final SerialConnectionBuilder serialConnectionBuilder;

    public boolean testSerialConnection(SerialConfiguration config) throws
                                                                    NotEnoughSerialPortsException,
                                                                    TooManyListenersException,
                                                                    UnsupportedCommOperationException,
                                                                    NoSuchPortException,
                                                                    PortInUseException,
                                                                    IOException,
                                                                    InterruptedException {
        String[] ports = serialConnectionBuilder.listAvailablePorts();
        if (ports.length < 2) {
            throw new NotEnoughSerialPortsException();
        }

        String port1 = ports[ports.length - 2];
        String port2 = ports[ports.length - 1];

        if (!testSerialConnection(port1, port2, config)) {
            return false;
        }
        if (!testSerialConnection(port2, port1, config)) {
            return false;
        }
        return true;
    }

    private boolean testSerialConnection(String readerPort, String writerPort, SerialConfiguration config)
            throws
            TooManyListenersException,
            UnsupportedCommOperationException,
            NoSuchPortException,
            PortInUseException, IOException, InterruptedException {
        log.info("Sending data from {} to {} with configuration {}.", readerPort, writerPort, config);

        TestByteListener tbl = new TestByteListener();

        try (SerialPortReader reader = serialConnectionBuilder.configure(readerPort, config).buildReader().register(tbl);
             SerialPortWriter writer = serialConnectionBuilder.configure(writerPort, config).buildWriter()) {

            for (int x = 0; x < 128; x++) {
                writer.write((byte) x);
                Thread.sleep(1);
            }

            Thread.sleep(100);
        }

        return tbl.assertValid();
    }

    private static class TestByteListener implements ByteListener {

        private final byte[] data = new byte[128];
        private int size = 0;

        @Override
        public void accept(byte b) {
            data[size] = b;
            size++;
        }

        public boolean assertValid() {
            if (size != 128) {
                log.error("Did not get all bytes. Received {}", size);
                return false;
            }
            for (int x = 0; x < data.length; x++) {
                if (data[x] != x) {
                    log.error("Byte at position {} should be {} but was {}.", x, x, data[x]);
                    return false;
                }
            }
            return true;
        }
    }
}
