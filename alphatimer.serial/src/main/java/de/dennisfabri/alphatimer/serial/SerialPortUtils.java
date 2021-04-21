package de.dennisfabri.alphatimer.serial;

import de.dennisfabri.alphatimer.serial.exceptions.NoPortsFoundException;
import gnu.io.CommPortIdentifier;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
class SerialPortUtils {
    static String[] listAvailablePorts() {
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

    static String autoconfigurePort() {
        log.info("  No serial port specified: Using autoconfiguration");
        String[] ports = SerialPortUtils.listAvailablePorts();
        if (ports.length == 0) {
            log.error("  No serial ports found.");
            throw new NoPortsFoundException();
        }
        log.info("  Available serial Ports: {}", String.join(", ", ports));
        return ports[ports.length - 1];
    }
}
