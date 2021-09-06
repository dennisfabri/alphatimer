package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.datatests.TestData;
import org.lisasp.alphatimer.messagesstorage.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApplicationTests {

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private Messages messages;

    @BeforeAll
    static void prepareData() throws IOException {
        new TestData().prepare("DM2010.serial");
    }

    @AfterAll
    static void cleanup() throws IOException {
        new TestData().cleanup();
    }

    @Test
    void contextLoads() {
        assertNotNull(context);
        assertNotNull(messages);
    }
}
