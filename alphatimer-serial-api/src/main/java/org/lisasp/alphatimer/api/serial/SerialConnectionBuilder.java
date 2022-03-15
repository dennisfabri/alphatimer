package org.lisasp.alphatimer.api.serial;

import org.lisasp.alphatimer.api.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.api.serial.exceptions.NoPortsFoundException;
import org.lisasp.alphatimer.api.serial.exceptions.PortAccessException;

public interface SerialConnectionBuilder {
    SerialConnectionBuilder configure(String port, SerialConfiguration config);

    SerialPortReader buildReader() throws PortAccessException;

    SerialPortWriter buildWriter() throws PortAccessException;

    String[] listAvailablePorts();

    String autoconfigurePort() throws NoPortsFoundException;
}
