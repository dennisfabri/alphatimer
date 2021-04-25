package de.dennisfabri.alphatimer.api.protocol.events.messages.values;

import lombok.EqualsAndHashCode;

import java.util.Arrays;

@EqualsAndHashCode
public class UsedLanes {

    private final boolean[] data;

    public UsedLanes() {
        this.data = new boolean[10];
    }

    public UsedLanes(boolean[] data) {
        this.data = Arrays.copyOf(data, 10);
    }

    public boolean isUsed(int x) {
        if (x >= data.length) {
            return false;
        }
        return data[x];
    }

    public int size() {
        return data.length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(12 + data.length);
        sb.append("UsedLanes(");
        for (boolean b : data) {
            sb.append(b ? "1" : 0);
        }
        sb.append(")");
        return sb.toString();
    }
}
