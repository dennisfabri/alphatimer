package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.messagesstorage.AresMessageRepository;
import de.dennisfabri.alphatimer.messagesstorage.Messages;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestServiceTest {

    @Test
    void getLegacyHeatsTest() {
        RestService restService = new RestService(new SerialInterpreter(new Messages(Mockito.mock(AresMessageRepository.class)),
                                                                        Mockito.mock(SerialConnectionBuilder.class),
                                                                        new ConfigurationValues())
        );

        String actual = restService.getLegacyHeats();

        assertEquals("<AlphaServer.Heat-array/>", actual);
    }
}
