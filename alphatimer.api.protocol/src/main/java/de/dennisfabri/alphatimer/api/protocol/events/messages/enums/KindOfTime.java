package de.dennisfabri.alphatimer.api.protocol.events.messages.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum KindOfTime {

    Start('S'),
    SplitTime('I'),
    Finish('A'),
    TakeOverTime('D'),
    ReactionTimeAtStart('R'),
    Empty(' '),
    UnknownValueB('B');

    @Getter
    private final char value;
}
