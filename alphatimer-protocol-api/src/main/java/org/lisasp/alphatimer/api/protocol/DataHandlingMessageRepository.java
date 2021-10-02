package org.lisasp.alphatimer.api.protocol;

import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;

import java.util.List;

public interface DataHandlingMessageRepository {
    void put(DataHandlingMessage message);

    List<DataHandlingMessage> findBy(String competitionKey, short event, byte heat);
}
