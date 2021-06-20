package org.lisasp.alphatimer.serial;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.serial.exceptions.NoPortsFoundException;

public interface SerialConnectionBuilder {
    SerialConnectionBuilder configure(String port, SerialConfiguration config);

    SerialPortReader buildReader() throws NoSuchPortException, PortInUseException,
                                          UnsupportedCommOperationException;

    SerialPortWriter buildWriter() throws NoSuchPortException, PortInUseException,
                                          UnsupportedCommOperationException;

    String[] listAvailablePorts();

    String autoconfigurePort() throws NoPortsFoundException;
}
