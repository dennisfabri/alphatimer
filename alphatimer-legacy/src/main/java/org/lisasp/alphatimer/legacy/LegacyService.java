package org.lisasp.alphatimer.legacy;

import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.ares.serial.DataHandlingMessageListener;
import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.KindOfTime;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.MessageType;
import org.lisasp.alphatimer.legacy.dto.Heat;
import org.lisasp.alphatimer.legacy.dto.LaneStatus;
import org.lisasp.alphatimer.legacy.entity.LaneTimeEntity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LegacyService implements DataHandlingMessageListener {

    private final LegacyRepository repository;

    private String currentCompetition = "";

    public LegacyService(LegacyRepository repository, String competitionKey) {
        this.repository = repository;
        this.currentCompetition = competitionKey;

        log.info("Selected competition: {}", currentCompetition);
    }

    @Override
    public void accept(DataHandlingMessage message) {
        if (message.getMessageType() == MessageType.OfficialEnd) {
            return;
        }
        if (message.getKindOfTime() != KindOfTime.Finish) {
            return;
        }
        if (message.getEvent() <= 0) {
            return;
        }
        if (message.getHeat() <= 0) {
            return;
        }

        short event = message.getEvent();
        byte heat = message.getHeat();
        LaneStatus status;
        switch (message.getMessageType()) {
            case OnLineTime:
                status = LaneStatus.RaceTimes;
                break;
            case CurrentRaceResults:
                status = LaneStatus.ResultsOfTheRace;
                break;
            case CurrentRaceResultsWithBackupTimes:
                status = LaneStatus.ResultsWithBackupTimes;
                break;
            case PreviousRaceResults:
                status = LaneStatus.ResultsOfThePreviousRace;
                break;
            case PreviousRaceResultsWithBackupTimes:
                status = LaneStatus.BackupOfThePreviousRace;
                break;
            default:
                status = LaneStatus.NotUsed;
                break;
        }

        LaneTimeEntity entity = new LaneTimeEntity();
        entity.setCompetition(message.getCompetition());
        entity.setEvent(event);
        entity.setHeatNumber(heat);
        entity.setLaneIndex(message.getLane() - 1);
        entity.setTimeInMillis(message.getTimeInMillis());
        entity.setStatus(status);
        entity.setTimestamp(message.getTimestamp());
        repository.save(entity);

        currentCompetition = message.getCompetition();
    }

    public Heat[] getHeats() {
        HashMap<String, Heat> heats = new HashMap<>();

        List<LaneTimeEntity> laneTimes = repository.findAllByCompetition(currentCompetition).stream().sorted().collect(Collectors.toList());
        for (LaneTimeEntity laneTime : laneTimes) {
            Heat heat = heats.computeIfAbsent(Heat.computeKey(laneTime.getEvent(), laneTime.getHeatNumber()), key ->
                    new Heat("" + (heats.size() + 1), laneTime.getEvent(), laneTime.getHeatNumber())
            );

            heat.store(laneTime.getLaneIndex(), laneTime.getTimeInMillis(), laneTime.getStatus());
        }

        return heats.values().stream().sorted().toArray(Heat[]::new);
    }
}
