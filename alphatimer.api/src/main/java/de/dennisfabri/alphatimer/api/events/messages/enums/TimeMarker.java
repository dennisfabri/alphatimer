package de.dennisfabri.alphatimer.api.events.messages.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TimeMarker {
    Empty(" "),
    Plus("+"),
    Minus("-"),
    DidNotStart("DNS"),
    DidNotFinish("DNF");

    @Getter
    private final String value;
}
