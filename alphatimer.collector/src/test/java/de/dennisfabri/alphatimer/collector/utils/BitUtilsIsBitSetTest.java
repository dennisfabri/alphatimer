package de.dennisfabri.alphatimer.collector.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BitUtilsIsBitSetTest {

    private BitUtils bitUtils;

    @BeforeEach
    void initialize() {
        bitUtils = new BitUtils();
    }

    @Test
    void isBit0SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b00000001, 0);

        assertTrue(actual);
    }

    @Test
    void isBit1SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b00000010, 1);

        assertTrue(actual);
    }

    @Test
    void isBit2SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b00000100, 2);

        assertTrue(actual);
    }

    @Test
    void isBit3SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b00001000, 3);

        assertTrue(actual);
    }

    @Test
    void isBit4SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b00010000, 4);

        assertTrue(actual);
    }

    @Test
    void isBit5SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b00100000, 5);

        assertTrue(actual);
    }

    @Test
    void isBit6SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b01000000, 6);

        assertTrue(actual);
    }

    @Test
    void isBit7SetTrue() {
        boolean actual = bitUtils.isBitSet((byte) 0b10000000, 7);

        assertTrue(actual);
    }

    @Test
    void isBit0SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b11111110, 0);

        assertFalse(actual);
    }

    @Test
    void isBit1SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b11111101, 1);

        assertFalse(actual);
    }

    @Test
    void isBit2SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b11111011, 2);

        assertFalse(actual);
    }

    @Test
    void isBit3SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b11110111, 3);

        assertFalse(actual);
    }

    @Test
    void isBit4SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b11101111, 4);

        assertFalse(actual);
    }

    @Test
    void isBit5SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b11011111, 5);

        assertFalse(actual);
    }

    @Test
    void isBit6SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b10111111, 6);

        assertFalse(actual);
    }

    @Test
    void isBit7SetFalse() {
        boolean actual = bitUtils.isBitSet((byte) 0b01111111, 7);

        assertFalse(actual);
    }
}
