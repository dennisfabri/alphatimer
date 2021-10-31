package org.lisasp.alphatimer.api.ares.serial.events.messages.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
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
    UnknownValue1('1'),
    Empty(' ');

    private final char value;
}
