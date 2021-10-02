package org.lisasp.alphatimer.messagesstorage;

import java.util.List;

public interface AresMessageRepository {
    List<AresMessage> findAllByCompetitionKeyAndEventAndHeat(String competitionKey, short event, byte heat);

    <S extends AresMessage> S save(S s);
    long count();
}
