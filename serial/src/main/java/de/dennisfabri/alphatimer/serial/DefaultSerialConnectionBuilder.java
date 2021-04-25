package de.dennisfabri.alphatimer.serial;

import de.dennisfabri.alphatimer.serial.configuration.SerialConfiguration;
import de.dennisfabri.alphatimer.serial.exceptions.NoPortsFoundException;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

@Slf4j
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
        List<String> ports = new ArrayList<>();

        Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                ports.add(portId.getName());
            }
        }

        return ports.stream().sorted().toArray(String[]::new);
    }

    @Override
    public String autoconfigurePort() throws NoPortsFoundException {
        log.info("  No serial port specified: Using autoconfiguration");
        String[] ports = listAvailablePorts();
        if (ports.length == 0) {
            log.error("  No serial ports found.");
            throw new NoPortsFoundException();
        }
        log.info("  Available serial Ports: {}", String.join(", ", ports));
        return ports[ports.length - 1];
    }
}
