package de.dennisfabri.alphatimer.legacy;

import de.dennisfabri.alphatimer.api.protocol.DataListener;
import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.KindOfTime;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.MessageType;
import de.dennisfabri.alphatimer.legacy.model.Heat;
import de.dennisfabri.alphatimer.legacy.model.LaneStatus;

import java.util.ArrayList;
import java.util.List;

public class LegacyTimeStorage implements DataListener {

    private final List<Heat> heats = new ArrayList<>();

    @Override
    public void notify(DataInputEvent event) {
        if (event instanceof DataHandlingMessage) {
            store((DataHandlingMessage) event);
        }
    }

    private void store(DataHandlingMessage message) {
        if (message.getMessageType() == MessageType.OfficialEnd) {
            return;
        }
        if (message.getKindOfTime() != KindOfTime.Finish) {
            return;
        }
        short event = message.getEvent() == 0 ? -1 : message.getEvent();
        byte heat = message.getHeat() == 0 ? -1 : message.getHeat();
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

        if (heats.isEmpty() || getCurrentHeat().getEvent() != event || getCurrentHeat().getHeat() != heat) {
            heats.add(new Heat("" + (heats.size() + 1), event, heat));
        }

        getCurrentHeat().store(message.getLane() - 1, message.getTimeInMillis(), status);
    }

    private Heat getCurrentHeat() {
        return heats.get(heats.size() - 1);
    }

    public Heat[] getHeats() {
        return heats.toArray(Heat[]::new);
    }
}
