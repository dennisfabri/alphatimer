package org.lisasp.alphatimer.test.serial;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.serial.com.DefaultSerialConnectionBuilder;
import org.lisasp.alphatimer.api.serial.exceptions.NoPortsFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultSerialConnectionBuilderTest {

    @Test
    void listAvailablePorts() {
        if (!TestUtil.isTestWithSerialHardwareEnabled()) {
            return;
        }
        assertNotNull(new DefaultSerialConnectionBuilder().listAvailablePorts());
    }

    @Test
    void autoconfigurePort() throws NoPortsFoundException {
        if (!TestUtil.isTestWithSerialHardwareEnabled()) {
            return;
        }
        assertNotNull(new DefaultSerialConnectionBuilder().autoconfigurePort());
    }
}
