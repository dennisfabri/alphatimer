package org.lisasp.alphatimer.api.protocol.events.messages.values;

import lombok.EqualsAndHashCode;

import java.util.Arrays;

@EqualsAndHashCode
public class UsedLanes {

    private static final String Prefix = "UsedLanes(";
    private static final String Suffix = ")";

    private static final int DataLength = 10;
    private static final int TextLength = Prefix.length() + DataLength + Suffix.length();

    private final boolean[] data;

    public UsedLanes() {
        this.data = new boolean[DataLength];
    }

    public UsedLanes(boolean[] data) {
        this.data = Arrays.copyOf(data, DataLength);
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
        StringBuilder sb = new StringBuilder(TextLength + 1);
        sb.append(Prefix);
        for (boolean b : data) {
            sb.append(b ? '1' : '0');
        }
        sb.append(Suffix);
        return sb.toString();
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder(TextLength + 1);
        for (boolean b : data) {
            sb.append(b ? '1' : '0');
        }
        return sb.toString();
    }

    public static UsedLanes fromValue(String data) {
        if (data.length() != DataLength) {
            throw new IllegalArgumentException(String.format("String must be exactly %d characters long.", DataLength));
        }

        char[] laneInfo = data.toCharArray();
        boolean[] result = new boolean[DataLength];
        for (int x = 0; x < laneInfo.length; x++) {
            result[x] = charToBoolean(laneInfo[x]);
        }
        return new UsedLanes(result);
    }

    private static boolean charToBoolean(char c) {
        switch (c) {
            case '0':
                return false;
            case '1':
                return true;
            default:
                throw new IllegalArgumentException("Only values '0' and '1' are allowed to specify lane usage.");
        }
    }
}
