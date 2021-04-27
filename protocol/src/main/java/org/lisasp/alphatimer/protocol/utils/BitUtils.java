package org.lisasp.alphatimer.protocol.utils;

import org.lisasp.alphatimer.protocol.exceptions.InvalidDataException;

public class BitUtils {

    public boolean[] extractUsedLanes(byte first, byte second) throws InvalidDataException {
        if (isBitSet(first, 7) || isBitSet(first, 6) || !isBitSet(first, 5)) {
            throw new InvalidDataException(
                    "DataHandling Message 1: The three highest bits for byte with index 6 are expected to be 001.");
        }
        if (isBitSet(second, 7) || isBitSet(second, 6) || !isBitSet(second, 5)) {
            throw new InvalidDataException(
                    "DataHandling Message 1: The three highest bits for byte with index 7 are expected to be 001.");
        }

        // The order of lanes in the bits is exactly reversed to the documentation
        // e.g. bit 2^0 is lane 5 for the first byte and lane 10 for the second byte instead of lane 1 and 6.
        boolean[] usedLanes = new boolean[10];
        for (byte pos = 0; pos < 5; pos++) {
            usedLanes[4 - pos] = isBitSet(first, pos);
        }
        for (byte pos = 0; pos < 5; pos++) {
            usedLanes[9 - pos] = isBitSet(second, pos);
        }
        return usedLanes;
    }

    boolean isBitSet(byte b, int bit) {
        return ((b >> bit) & 1) > 0;
    }
}
