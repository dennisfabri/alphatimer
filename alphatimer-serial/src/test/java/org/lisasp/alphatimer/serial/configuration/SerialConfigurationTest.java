package org.lisasp.alphatimer.serial.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerialConfigurationTest {

    @Test
    void toStringTest() {
        assertEquals("SerialConfiguration(baud=9600, databits=Seven, stopbits=One, parity=Even)",
                     SerialConfiguration.ARES21.toString());
    }

}
