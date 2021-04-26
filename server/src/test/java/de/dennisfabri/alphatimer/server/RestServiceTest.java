package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.protocol.InputCollector;
import de.dennisfabri.alphatimer.legacy.LegacyTimeStorage;
import de.dennisfabri.alphatimer.messagesstorage.AresMessageRepository;
import de.dennisfabri.alphatimer.messagesstorage.Messages;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import de.dennisfabri.alphatimer.storage.DateFacade;
import de.dennisfabri.alphatimer.storage.FileFacade;
import de.dennisfabri.alphatimer.storage.Storage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestServiceTest {

    @Test
    void getLegacyHeatsTest() {
        Storage storage = new Storage("test", Mockito.mock(FileFacade.class), Mockito.mock(DateFacade.class));
        RestService restService = new RestService(new SerialInterpreter(new Messages(Mockito.mock(AresMessageRepository.class)),
                                                                        Mockito.mock(SerialConnectionBuilder.class),
                                                                        new ConfigurationValues(),
                                                                        storage,
                                                                        new LegacyTimeStorage(),
                                                                        new InputCollector(),
                                                                        () -> LocalDate.of(2020,
                                                                                           Month.APRIL,
                                                                                           3)
        )
        );

        String actual = restService.getLegacyHeats();

        assertEquals("<AlphaServer.Heat-array/>", actual);
    }
}
