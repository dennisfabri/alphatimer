package org.lisasp.alphatimer.test.serial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.serial.DefaultSerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialLoopTester;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.serial.exceptions.NotEnoughSerialPortsException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SerialLoopTesterTest {

    @Test
    void test() throws Exception {
        if (!TestUtil.isTestWithSerialHardwareEnabled()) {
            return;
        }
        try {
            Assertions.assertTrue(new SerialLoopTester(new DefaultSerialConnectionBuilder())
                               .testSerialConnection(SerialConfiguration.TEST));
        } catch (NotEnoughSerialPortsException ex) {
            // This exception shows that the environment is not build up:
            // This test is for local testing only
        }
    }
}
