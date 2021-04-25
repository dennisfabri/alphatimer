package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.datatests.TestData;
import de.dennisfabri.alphatimer.messagesstorage.Messages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration()
class ApplicationTests {

    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private Messages messages;

    @BeforeAll
    static void prepareData() throws IOException {
        new TestData().prepare("DM2010.serial");
    }

    @Test
    void contextLoads() {
        assertNotNull(context);
        assertNotNull(messages);
    }

    @Test
    void serialFilesToDatabaseTest() throws IOException {
        context.getBean(SerialFilesToDatabase.class).transfer("target/test-data/");

        int actual = messages.get("DM2010", (short) 10, (byte) 1).size();

        assertEquals(26, actual);
    }
}
