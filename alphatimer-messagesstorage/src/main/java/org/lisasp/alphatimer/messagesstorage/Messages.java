package org.lisasp.alphatimer.messagesstorage;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageRepository;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Messages implements DataHandlingMessageRepository {

    private final AresMessageRepository repository;

    @Override
    public void put(DataHandlingMessage message) {
        repository.save(mapDataHandlingMessageToAresMessage(message));
    }

    @Override
    public List<DataHandlingMessage> findBy(String competition, short event, byte heat) {
        return repository.findAllByCompetitionKeyAndEventAndHeat(competition, event, heat)
                         .stream()
                         .map(am -> mapAresMessageToDataHandlingMessage(am))
                         .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return (int) repository.count();
    }

    private AresMessage mapDataHandlingMessageToAresMessage(DataHandlingMessage message) {
        return new AresMessage(message.getOriginalText1(),
                               message.getOriginalText2(),
                               message.getTimestamp(),
                               message.getMessageType(),
                               message.getKindOfTime(),
                               message.getTimeType(),
                               toString(message.getUsedLanes()),
                               message.getLapCount(),
                               message.getEvent(),
                               message.getHeat(),
                               message.getRank(),
                               message.getRankInfo(),
                               message.getLane(),
                               message.getCurrentLap(),
                               message.getTimeInMillis(),
                               message.getTimeInfo(),
                               message.getTimeMarker(),
                               message.getCompetition());
    }

    private DataHandlingMessage mapAresMessageToDataHandlingMessage(AresMessage message) {
        return new DataHandlingMessage(message.getOriginalText1(),
                                       message.getOriginalText2(),
                                       message.getTimestamp(),
                                       message.getCompetitionKey(),
                                       message.getMessageType(),
                                       message.getKindOfTime(),
                                       message.getTimeType(),
                                       toUsedLanes(message.getUsedLanes()),
                                       message.getLapCount(),
                                       message.getEvent(),
                                       message.getHeat(),
                                       message.getRank(),
                                       message.getRankInfo(),
                                       message.getLane(),
                                       message.getCurrentLap(),
                                       message.getTimeInMillis(),
                                       message.getTimeInfo(),
                                       message.getTimeMarker());
    }

    private UsedLanes toUsedLanes(String usedLanes) {
        boolean[] bitSet = new boolean[10];

        for (int x = 0; x < usedLanes.length(); x++) {
            bitSet[x] = usedLanes.toCharArray()[x] == '+';
        }
        return new UsedLanes(bitSet);
    }

    private String toString(UsedLanes usedLanes) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < 10; x++) {
            sb.append(usedLanes.isUsed(x) ? "+" : "-");
        }
        return sb.toString();
    }
}
