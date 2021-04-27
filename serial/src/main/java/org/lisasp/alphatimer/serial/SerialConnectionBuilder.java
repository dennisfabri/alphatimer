package org.lisasp.alphatimer.serial;

import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.serial.exceptions.NoPortsFoundException;
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

    String autoconfigurePort() throws NoPortsFoundException;
}
