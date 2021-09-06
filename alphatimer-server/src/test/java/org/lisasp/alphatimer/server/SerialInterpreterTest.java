package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageRepository;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.legacy.LegacyRepository;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.server.mq.Sender;
import org.lisasp.alphatimer.server.testdoubles.TestDateFacade;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SerialInterpreterTest {

    @Autowired
    private LegacyRepository repository;

    private MessageConverter messageConverter;
    private InputCollector inputCollector;
    private SerialInterpreter serialInterpreter;
    private DataHandlingMessageRepository messages;
    private ConfigurationValues config;

    private final static String expectedDate = "2021-04-17";

    @BeforeEach
    void prepare() {
        DateTimeFacade dateTimeFacade = mock(DateTimeFacade.class);
        when(dateTimeFacade.now()).thenReturn(LocalDateTime.of(2021, 6, 21, 14, 53));

        config = new ConfigurationValues(new TestDateFacade());
        // config.setStoragePath("target/test-SerialInterpreterTest");

        messages = Mockito.mock(DataHandlingMessageRepository.class);

        messageConverter = new MessageConverter();

        inputCollector = new InputCollector("TestWK", dateTimeFacade);
        inputCollector.register(messageConverter);

        serialInterpreter = new SerialInterpreter(messages,
                                                  config,
                                                  new LegacyService(repository),
                                                  messageConverter,
                                                  new DataHandlingMessageRefiner(),
                                                  Mockito.mock(Sender.class)
        );
    }

    @Test
    void getLegacyData() {
        String result = serialInterpreter.getLegacyData();
        assertEquals("<AlphaServer.Heat-array/>", result);
    }

    @Test
    void start() {
        serialInterpreter.start();

        Mockito.verify(messages, times(0)).put(any());
    }

    @Test
    void startWithAutoconfigure() {
        serialInterpreter.start();

        Mockito.verify(messages, times(0)).put(any());
    }

    @Test
    void startAndSend() {
        serialInterpreter.start();

        for (byte b : DataHandlingMessageTestData.message1) {
            inputCollector.accept(b);
        }
        for (byte b : DataHandlingMessageTestData.message2) {
            inputCollector.accept(b);
        }

        Mockito.verify(messages, times(1)).put(any());
    }

    @Test
    void onDestroy() {
        serialInterpreter.start();
        serialInterpreter.onDestroy();

        Mockito.verify(messages, times(0)).put(any());
    }
}
