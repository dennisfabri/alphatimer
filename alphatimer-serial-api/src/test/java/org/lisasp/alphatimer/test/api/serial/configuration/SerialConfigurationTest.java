package org.lisasp.alphatimer.test.api.serial.configuration;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.serial.configuration.SerialConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerialConfigurationTest {

    @Test
    void toStringTest() {
        assertEquals("SerialConfiguration(baud=9600, databits=Seven, stopbits=One, parity=Even)",
                     SerialConfiguration.ARES21.toString());
    }

}
