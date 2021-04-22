package de.dennisfabri.alphatimer.api.protocol.events.messages.enums;

import lombok.Getter;

public enum MessageType {

    ReadyToStart(0),
    OfficialEnd(1),
    OnLineTime(2),
    CurrentRaceResults(3),
    CurrentRaceResultsWithBackupTimes(4),
    PreviousRaceResults(5),
    PreviousRaceResultsWithBackupTimes(6),
    UnknownValue7(7);

    @Getter
    private final byte value;

    MessageType(int value) {
        this.value = (byte) value;
    }
}
