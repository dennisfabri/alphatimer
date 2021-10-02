package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.messagesstorage.AresMessageRepository;
import org.lisasp.alphatimer.messagesstorage.Messages;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.server.mq.Sender;
import org.lisasp.alphatimer.server.testdoubles.TestDateFacade;
import org.mockito.Mockito;
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
                        new Messages(Mockito.mock(AresMessageRepository.class)),
                        new ConfigurationValues(new TestDateFacade()),
                        new LegacyService(repository),
                        new MessageConverter(),
                        new DataHandlingMessageRefiner(),
                        Mockito.mock(Sender.class)
                )
        );

        String actual = restService.getLegacyHeats();

        assertEquals("<AlphaServer.Heat-array/>", actual);
    }
}
