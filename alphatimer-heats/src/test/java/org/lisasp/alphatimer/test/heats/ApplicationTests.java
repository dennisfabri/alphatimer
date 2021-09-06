package org.lisasp.alphatimer.test.heats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration
public class ApplicationTests {

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }
}
