package org.lisasp.alphatimer.api.protocol.events.messages.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RankInfo {
    Normal(' '),
    Disqualified('D');

    private final char value;
}
