package org.lisasp.alphatimer.server;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.legacy.LegacyTimeStorage;
import org.lisasp.alphatimer.messagesstorage.AresMessageRepository;
import org.lisasp.alphatimer.messagesstorage.Messages;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.server.testdoubles.TestDateFacade;
import org.lisasp.alphatimer.storage.DateFacade;
import org.lisasp.alphatimer.storage.FileFacade;
import org.lisasp.alphatimer.storage.Storage;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestServiceTest {

    @Test
    void getLegacyHeatsTest() {
        Storage storage = new Storage("test", Mockito.mock(FileFacade.class), Mockito.mock(DateFacade.class));
        RestService restService = new RestService(
                new SerialInterpreter(
                        new Messages(Mockito.mock(AresMessageRepository.class)),
                        Mockito.mock(SerialConnectionBuilder.class),
                        new ConfigurationValues(new TestDateFacade()),
                        storage,
                        new LegacyTimeStorage(),
                        new InputCollector()
                )
        );

        String actual = restService.getLegacyHeats();

        assertEquals("<AlphaServer.Heat-array/>", actual);
    }
}
