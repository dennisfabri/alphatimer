package de.dennisfabri.alphatimer.api.protocol.events.messages.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RankInfo {
    Normal(' '),
    UnknownValueD('D');

    @Getter
    private final char value;
}
