package de.dennisfabri.alphatimer.api.protocol.events.messages.values;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UsedLanesTest {


    private BitSet createBitSet(boolean[] values) {
        BitSet bs = new BitSet();
        for (int x = 0; x < values.length; x++) {
            bs.set(x, values[x]);
        }
        return bs;
    }

    @Test
    void isUsedTest() {
        final boolean[] data = new boolean[]{true, false, true, true, false, true, false, false, true, false};
        UsedLanes lanes1 = new UsedLanes(createBitSet(data));
        for (int x = 0; x < data.length; x++) {
            assertEquals(data[x], lanes1.isUsed(x));
        }
    }

    @Test
    void equalsTest() {
        UsedLanes lanes1 = new UsedLanes(createBitSet(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        UsedLanes lanes2 = new UsedLanes(createBitSet(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        assertEquals(lanes1, lanes2);
    }

    @Test
    void notEqualsTest() {
        UsedLanes lanes1 = new UsedLanes(createBitSet(new boolean[]{true, false, true, true, true, true, true, true, true, false}));
        UsedLanes lanes2 = new UsedLanes(createBitSet(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        assertNotEquals(lanes1, lanes2);
    }


    @Test
    void TenLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{true, true, true, true, true, true, true, true, true, true}));
        assertEquals("UsedLanes(lanes=[true, true, true, true, true, true, true, true, true, true])", lanes.toString());
    }

    @Test
    void NineLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        assertEquals("UsedLanes(lanes=[true, true, true, true, true, true, true, true, true, false])",
                     lanes.toString());
    }

    @Test
    void EightLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, true, true, true, true, true, true, true, true, false}));
        assertEquals("UsedLanes(lanes=[false, true, true, true, true, true, true, true, true, false])",
                     lanes.toString());
    }

    @Test
    void SevenLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, true, true, true, true, true, true, true, false, false}));
        assertEquals("UsedLanes(lanes=[false, true, true, true, true, true, true, true, false, false])",
                     lanes.toString());
    }

    @Test
    void SixLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, true, true, true, true, true, true, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, true, true, true, true, true, true, false, false])",
                     lanes.toString());
    }

    @Test
    void FiveLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, true, true, true, true, true, false, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, true, true, true, true, true, false, false, false])",
                     lanes.toString());
    }

    @Test
    void FourLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, false, true, true, true, true, false, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, false, true, true, true, true, false, false, false])",
                     lanes.toString());
    }

    @Test
    void ThreeLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, false, true, true, true, false, false, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, false, true, true, true, false, false, false, false])",
                     lanes.toString());
    }

    @Test
    void TwoLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, false, false, true, true, false, false, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, false, false, true, true, false, false, false, false])",
                     lanes.toString());
    }

    @Test
    void OneLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, false, false, true, false, false, false, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, false, false, true, false, false, false, false, false])",
                     lanes.toString());
    }

    @Test
    void ZeroLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, false, false, false, false, false, false, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, false, false, false, false, false, false, false, false])",
                     lanes.toString());
    }

    @Test
    void ZeroLanesUsedEmptyTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{}));
        assertEquals("UsedLanes(lanes=[false, false, false, false, false, false, false, false, false, false])",
                     lanes.toString());
    }

    @Test
    void OneLanesUsedPartialTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{false, false, false, false, true, false, false, false}));
        assertEquals("UsedLanes(lanes=[false, false, false, false, true, false, false, false, false, false])",
                     lanes.toString());
    }

    @Test
    void ZeroLanesPartialUsedTest() {
        UsedLanes lanes = new UsedLanes(createBitSet(new boolean[]{}));
        assertEquals("UsedLanes(lanes=[false, false, false, false, false, false, false, false, false, false])",
                     lanes.toString());
    }
}
