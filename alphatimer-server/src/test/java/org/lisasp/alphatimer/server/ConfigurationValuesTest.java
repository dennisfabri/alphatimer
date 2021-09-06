package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.lisasp.alphatimer.server.testdoubles.TestDateFacade;

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

}
