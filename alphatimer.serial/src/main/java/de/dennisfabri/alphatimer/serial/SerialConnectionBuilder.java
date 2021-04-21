package de.dennisfabri.alphatimer.serial;

import de.dennisfabri.alphatimer.serial.configuration.SerialConfiguration;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.util.TooManyListenersException;

public interface SerialConnectionBuilder {
    SerialConnectionBuilder configure(String port, SerialConfiguration config);

    SerialPortReader buildReader(ByteListener listener) throws NoSuchPortException, PortInUseException,
                                                               TooManyListenersException,
                                                               UnsupportedCommOperationException;

    SerialPortWriter buildWriter() throws NoSuchPortException, PortInUseException,
                                          UnsupportedCommOperationException;

    String[] listAvailablePorts();

    String autoconfigurePort();
}
