package de.dennisfabri.alphatimer.serial;

import de.dennisfabri.alphatimer.serial.configuration.SerialConfiguration;
import de.dennisfabri.alphatimer.serial.exceptions.NotEnoughSerialPortsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SerialLoopTesterTest {

    @Test
    void test() throws Exception {
        if (!TestUtil.isTestWithSerialHardwareEnabled()) {
            return;
        }
        try {
            assertTrue(new SerialLoopTester(new DefaultSerialConnectionBuilder())
                               .testSerialConnection(SerialConfiguration.TEST));
        } catch (NotEnoughSerialPortsException ex) {
            // This exception shows that the environment is not build up:
            // This test is for local testing only
        }
    }
}
