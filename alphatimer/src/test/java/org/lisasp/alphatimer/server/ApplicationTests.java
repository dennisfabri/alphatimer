package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.messagesstorage.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApplicationTests {

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private Messages messages;

    @Test
    void contextLoads() {
        assertNotNull(context);
        assertNotNull(messages);
    }
}
