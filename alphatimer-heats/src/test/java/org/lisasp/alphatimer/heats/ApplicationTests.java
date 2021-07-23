package org.lisasp.alphatimer.heats;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicationTests {

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }
}
