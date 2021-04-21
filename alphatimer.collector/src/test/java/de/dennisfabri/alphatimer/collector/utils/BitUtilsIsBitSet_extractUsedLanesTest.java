package de.dennisfabri.alphatimer.collector.utils;

import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitUtilsIsBitSet_extractUsedLanesTest {

    private BitUtils bitUtils;

    @BeforeEach
    void initialize() {
        bitUtils = new BitUtils();
    }

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

    @Test
    void extractUsedLanes1() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(0, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00110000, (byte) 0b00100000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes2() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(1, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00101000, (byte) 0b00100000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes3() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(2, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100100, (byte) 0b00100000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes4() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(3, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100010, (byte) 0b00100000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes5() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(4, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100001, (byte) 0b00100000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes6() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(5, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100000, (byte) 0b00110000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes7() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(6, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100000, (byte) 0b00101000);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes8() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(7, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100000, (byte) 0b00100100);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes9() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(8, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100000, (byte) 0b00100010);

        assertEquals(expected, actual);
    }

    @Test
    void extractUsedLanes10() throws InvalidDataException {
        BitSet expected = new BitSet();
        expected.set(9, false);
        expected.set(9, true);

        BitSet actual = bitUtils.extractUsedLanes((byte) 0b00100000, (byte) 0b00100001);

        assertEquals(expected, actual);
    }
}
