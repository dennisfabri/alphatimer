package org.lisasp.alphatimer.api.protocol.events.messages.enums

enum class KindOfTime(val value: Char) {
    Start('S'),
    SplitTime('I'),
    Finish('A'),
    TakeOverTime('D'),
    ReactionTimeAtStart('R'),
    Empty(' '),
    UnknownValueB('B');
}
