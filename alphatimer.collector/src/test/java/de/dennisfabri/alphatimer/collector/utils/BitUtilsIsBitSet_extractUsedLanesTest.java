package de.dennisfabri.alphatimer.collector.utils;

import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitUtilsIsBitSet_extractUsedLanesTest {

    private BitUtils bitUtils;

    @BeforeEach
    void initialize() {
        bitUtils = new BitUtils();
    }

    private final static byte[] SELECTED_LANE_DATA = new byte[]{0b00110000, 0b00101000, 0b00100100, 0b00100010, 0b00100001};

    @Test
    void extractUsedLanesNone() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100000, (byte) 0b00100000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanesC4() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(1, true);
        expected.set(2, true);
        expected.set(7, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) (0b00100000 | 0x0C), (byte) (0b00100000 | 0x04));

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1, 2, 3, 4})
    void extractUsedLanesFromFirstByte(byte laneIndex) throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(laneIndex, true);

        BitSet actual = bitUtils.extractUsedLanes(SELECTED_LANE_DATA[laneIndex], (byte) 0b00100000);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1, 2, 3, 4})
    void extractUsedLanesFromSecondByte(byte laneIndex) throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(5 + laneIndex, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100000, SELECTED_LANE_DATA[laneIndex]);

        assertEquals(expected, actual);
    }
}
