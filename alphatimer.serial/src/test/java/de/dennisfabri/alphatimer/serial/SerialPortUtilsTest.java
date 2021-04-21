package de.dennisfabri.alphatimer.serial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SerialPortUtilsTest {

    @Test
    void listAvailablePorts() {
        if (!TestUtil.isTestWithSerialHardwareEnabled()) {
            return;
        }
        assertNotNull(SerialPortUtils.listAvailablePorts());
    }

    @Test
    void autoconfigurePort() {
        if (!TestUtil.isTestWithSerialHardwareEnabled()) {
            return;
        }
        assertNotNull(SerialPortUtils.autoconfigurePort());
    }
}
