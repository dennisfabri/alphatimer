package de.dennisfabri.alphatimer.api.protocol.data;

import lombok.EqualsAndHashCode;

import java.util.Arrays;

@EqualsAndHashCode
public class Bytes {
    private final byte[] data;

    public Bytes(byte[] data) {
        this.data = Arrays.copyOf(data, data.length);
    }

    public byte get(int x) {
        return data[x];
    }

    public String toString() {
        return "Bytes(" + Arrays.toString(this.data) + ")";
    }
}
