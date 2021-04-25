package de.dennisfabri.alphatimer.api.protocol.events.messages.enums

enum class MessageType(val value: Byte) {
    ReadyToStart(0),
    OfficialEnd(1),
    OnLineTime(2),
    CurrentRaceResults(3),
    CurrentRaceResultsWithBackupTimes(4),
    PreviousRaceResults(5),
    PreviousRaceResultsWithBackupTimes(6),
    UnknownValue7(7);
}
