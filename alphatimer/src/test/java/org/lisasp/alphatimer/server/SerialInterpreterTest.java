package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.DataHandlingMessageRepository;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.Storage;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.ares.serial.MessageConverter;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.basics.notification.primitive.ByteListener;
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
    private LegacyJPARepository repository;

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

        messages = Mockito.mock(DataHandlingMessageRepository.class);

        messageConverter = new MessageConverter();

        inputCollector = new InputCollector("TestWK", dateTimeFacade);
        inputCollector.register(messageConverter);

        SerialPortReader reader = new SerialPortReader() {
            @Override
            public SerialPortReader register(ByteListener listener) {
                return this;
            }

            @Override
            public void close() {

            }
        };

        serialInterpreter = new SerialInterpreter(reader,
                                                  mock(Storage.class),
                                                  mock(InputCollector.class),
                                                  messages,
                                                  new LegacyService(repository),
                                                  messageConverter,
                                                  new DataHandlingMessageRefiner()
        );
    }

    @Test
    void getLegacyData() {
        String result = serialInterpreter.getLegacyDataXML();
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

        Mockito.verify(messages, times(0)).put(any());
    }
}
