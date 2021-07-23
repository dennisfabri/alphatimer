package org.lisasp.alphatimer.spring.jms;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.spring.jms.data.TestData1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JsonMessageConverterTest {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @BeforeEach
    void prepare() {
        // sender = new Sender();
        // receiver = new Receiver();
    }

    @Test
    void testReceive() throws Exception {
        TestData1 value = new TestData1("Demo", 20);
        sender.send("converter.q", value);

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(0, receiver.getLatch().getCount());
    }
}
