package org.lisasp.alphatimer.api.ares.serial.events.messages.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TimeInfo {
    Normal(' '),
    Edited('E'),
    Backup('B'),
    Manual('M'),
    UnknownAsterisk('*');

    private final char value;
}
