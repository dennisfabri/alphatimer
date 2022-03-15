package org.lisasp.alphatimer.serial.com;

import gnu.io.CommPortIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.SerialPortWriter;
import org.lisasp.alphatimer.api.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.api.serial.exceptions.NoPortsFoundException;
import org.lisasp.alphatimer.api.serial.exceptions.PortAccessException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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
    public SerialPortReader buildReader() throws PortAccessException {
        return new SerialReader(port, config);
    }

    @Override
    public SerialPortWriter buildWriter() throws PortAccessException {
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
            } else {
                log.info("Other port found: {}", portId.getName());
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
