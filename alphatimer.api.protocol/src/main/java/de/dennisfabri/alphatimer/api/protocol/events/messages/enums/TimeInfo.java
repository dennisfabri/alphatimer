package de.dennisfabri.alphatimer.api.protocol.events.messages.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TimeInfo {
    Normal(' '),
    Edited('E'),
    Backup('B'),
    Manual('M'),
    UnknownAsterisk('*');

    @Getter
    private final char value;
}
