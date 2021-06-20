package org.lisasp.alphatimer.serial.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Parity {
    None((byte) 0),
    Odd((byte) 1),
    Even((byte) 2),
    Mark((byte) 3),
    Space((byte) 4);

    private final byte value;
}
