package de.dennisfabri.alphatimer.serial.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataBits {
    Five((byte) 5), Six((byte) 6), Seven((byte) 7), Eight((byte) 8);

    private final byte value;
}
