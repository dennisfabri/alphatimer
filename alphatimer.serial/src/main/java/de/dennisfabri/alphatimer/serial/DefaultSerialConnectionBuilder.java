package de.dennisfabri.alphatimer.serial;

import de.dennisfabri.alphatimer.serial.configuration.SerialConfiguration;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.util.TooManyListenersException;

public class DefaultSerialConnectionBuilder implements SerialConnectionBuilder {

    private String port;
    private SerialConfiguration config;

    @Override
    public SerialConnectionBuilder configure(String port, SerialConfiguration config) {
        this.port = port;
        this.config = config;
        return this;
    }

    @Override
    public SerialPortReader buildReader(ByteListener listener) throws
                                                               NoSuchPortException,
                                                               PortInUseException,
                                                               TooManyListenersException,
                                                               UnsupportedCommOperationException {
        return new SerialReader(port, config, listener);
    }

    @Override
    public SerialPortWriter buildWriter()
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        return new SerialWriter(port, config);
    }

    @Override
    public String[] listAvailablePorts() {
        return SerialPortUtils.listAvailablePorts();
    }

    @Override
    public String autoconfigurePort() {
        return SerialPortUtils.autoconfigurePort();
    }
}
