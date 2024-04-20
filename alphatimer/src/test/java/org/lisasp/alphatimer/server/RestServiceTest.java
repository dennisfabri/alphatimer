package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.Storage;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.messagesstorage.AresMessageRepository;
import org.lisasp.alphatimer.messagesstorage.Messages;
import org.lisasp.alphatimer.ares.serial.MessageConverter;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RestServiceTest {

    @Autowired
    private LegacyJPARepository repository;

    @Test
    void getLegacyHeatsTest() {
        RestService restService = new RestService(
                new SerialInterpreter(
                        mock(SerialPortReader.class),
                        mock(Storage.class),
                        mock(InputCollector.class),
                        new Messages(mock(AresMessageRepository.class)),
                        new LegacyService(repository, "TestWK"),
                        new MessageConverter(),
                        new DataHandlingMessageRefiner()
                )
        );

        String actual = restService.getLegacyHeats();

        assertEquals("<AlphaServer.Heat-array/>", actual);
    }
}
