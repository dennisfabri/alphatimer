package org.lisasp.alphatimer.api.protocol.events.messages.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UsedLanesTest {


    private boolean[] createBits(boolean[] values) {
        return values;
    }

    @Test
    void isUsedTest() {
        final boolean[] data = new boolean[]{true, false, true, true, false, true, false, false, true, false};
        UsedLanes lanes1 = new UsedLanes(createBits(data));
        for (int x = 0; x < data.length; x++) {
            assertEquals(data[x], lanes1.isUsed(x));
        }
    }

    @Test
    void equalsTest() {
        UsedLanes lanes1 = new UsedLanes(createBits(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        UsedLanes lanes2 = new UsedLanes(createBits(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        assertEquals(lanes1, lanes2);
    }

    @Test
    void notEqualsTest() {
        UsedLanes lanes1 = new UsedLanes(createBits(new boolean[]{true, false, true, true, true, true, true, true, true, false}));
        UsedLanes lanes2 = new UsedLanes(createBits(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        assertNotEquals(lanes1, lanes2);
    }


    @Test
    void TenLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{true, true, true, true, true, true, true, true, true, true}));
        assertEquals("UsedLanes(1111111111)", lanes.toString());
    }

    @Test
    void NineLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        assertEquals("UsedLanes(1111111110)",
                     lanes.toString());
    }

    @Test
    void EightLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, true, true, true, true, true, true, true, true, false}));
        assertEquals("UsedLanes(0111111110)",
                     lanes.toString());
    }

    @Test
    void SevenLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, true, true, true, true, true, true, true, false, false}));
        assertEquals("UsedLanes(0111111100)",
                     lanes.toString());
    }

    @Test
    void SixLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, true, true, true, true, true, true, false, false}));
        assertEquals("UsedLanes(0011111100)",
                     lanes.toString());
    }

    @Test
    void FiveLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, true, true, true, true, true, false, false, false}));
        assertEquals("UsedLanes(0011111000)",
                     lanes.toString());
    }

    @Test
    void FourLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, true, true, true, true, false, false, false}));
        assertEquals("UsedLanes(0001111000)",
                     lanes.toString());
    }

    @Test
    void ThreeLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, true, true, true, false, false, false, false}));
        assertEquals("UsedLanes(0001110000)",
                     lanes.toString());
    }

    @Test
    void TwoLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, true, true, false, false, false, false}));
        assertEquals("UsedLanes(0000110000)",
                     lanes.toString());
    }

    @Test
    void OneLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, true, false, false, false, false, false}));
        assertEquals("UsedLanes(0000100000)",
                     lanes.toString());
    }

    @Test
    void ZeroLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, false, false, false, false, false, false}));
        assertEquals("UsedLanes(0000000000)",
                     lanes.toString());
    }

    @Test
    void ZeroLanesUsedEmptyTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{}));
        assertEquals("UsedLanes(0000000000)",
                     lanes.toString());
    }

    @Test
    void OneLanesUsedPartialTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, true, false, false, false}));
        assertEquals("UsedLanes(0000100000)",
                     lanes.toString());
    }

    @Test
    void ZeroLanesPartialUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{}));
        assertEquals("UsedLanes(0000000000)",
                     lanes.toString());
    }
}
