package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.server.testdoubles.TestDateFacade;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigurationValuesTest {

    private ConfigurationValues config;

    @BeforeEach
    void prepare() {
        config = new ConfigurationValues(new TestDateFacade());
    }

    @AfterEach
    void cleanup() {
        config = null;
    }

    @Test
    void getSerialConfigurationObjectARES21Test() {
        config.setSerialConfiguration("ARES21");

        SerialConfiguration actual = config.getSerialConfigurationObject();

        assertEquals(SerialConfiguration.ARES21, actual);
    }

    @Test
    void getSerialConfigurationObjectQuantumTest() {
        config.setSerialConfiguration("Quantum");

        SerialConfiguration actual = config.getSerialConfigurationObject();

        assertEquals(SerialConfiguration.Quantum, actual);
    }

    @Test
    void getSerialConfigurationObjectTestTest() {
        config.setSerialConfiguration("Test");

        SerialConfiguration actual = config.getSerialConfigurationObject();

        assertEquals(SerialConfiguration.TEST, actual);
    }

}
