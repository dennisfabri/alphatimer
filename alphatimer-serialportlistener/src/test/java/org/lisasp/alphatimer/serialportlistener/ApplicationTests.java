package org.lisasp.alphatimer.serialportlistener;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApplicationTests {

    @Autowired
    private ConfigurableApplicationContext context;

    @BeforeAll
    static void prepareData() throws IOException {
    }

    @AfterAll
    static void cleanup() throws IOException {
    }

    @Test
    void contextLoads() {
        assertNotNull(context);
    }
}
