package org.lisasp.alphatimer.api.ares.serial;

import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;

import java.util.List;

public interface DataHandlingMessageRepository {
    void put(DataHandlingMessage message);

    List<DataHandlingMessage> findBy(String competitionKey, short event, byte heat);
}
