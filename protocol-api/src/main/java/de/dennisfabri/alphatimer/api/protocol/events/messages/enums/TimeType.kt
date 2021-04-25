package de.dennisfabri.alphatimer.api.protocol.events.messages.enums

enum class TimeType(val value: Char) {
    CorrectedTime('C'),
    DeletedTime('E'),
    FalseStartAtRelay('F'),
    InsertedTime('I'),
    ManualTime('M'),
    TimeIsMissing('N'),
    OneOrTwoPushButtonTimesAreMissing('T'),
    PlatformTimeAfterTouchpadTime('+'),
    PlatformTimeBeforeTouchpadTime('-'),
    UnkownValue1('1'),
    Empty(' ');
}
