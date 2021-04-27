package org.lisasp.alphatimer.api.protocol.events.messages.enums

enum class TimeMarker(val value: String) {
    Empty(" "),
    Plus("+"),
    Minus("-"),
    DidNotStart("DNS"),
    DidNotFinish("DNF");
}
