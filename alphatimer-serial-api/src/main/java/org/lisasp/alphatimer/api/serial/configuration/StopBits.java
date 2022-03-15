package org.lisasp.alphatimer.api.serial.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StopBits {
    One((byte) 1),
    Two((byte) 2),
    OneFive((byte) 3);

    private final byte value;
}
