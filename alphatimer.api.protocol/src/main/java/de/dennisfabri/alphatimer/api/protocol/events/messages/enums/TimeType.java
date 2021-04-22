package de.dennisfabri.alphatimer.api.protocol.events.messages.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TimeType {
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

    @Getter
    private final char value;
}
