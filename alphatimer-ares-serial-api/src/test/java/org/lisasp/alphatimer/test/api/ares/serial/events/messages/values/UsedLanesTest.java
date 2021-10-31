package org.lisasp.alphatimer.test.api.ares.serial.events.messages.values;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lisasp.alphatimer.api.ares.serial.events.messages.values.UsedLanes;

import static org.junit.jupiter.api.Assertions.*;

class UsedLanesTest {


    private boolean[] createBits(boolean[] values) {
        return values;
    }

    @Test
    void emptyConstructor() {
        new UsedLanes();
    }

    @Test
    void parameterConstructor1() {
        final boolean[] data = new boolean[]{true};
        UsedLanes lanes = new UsedLanes(createBits(data));
    }

    @Test
    void parameterConstructor10() {
        final boolean[] data = new boolean[]{true, false, true, true, false, true, false, false, true, false};
        UsedLanes lanes = new UsedLanes(createBits(data));
    }

    @Test
    void size() {
        final boolean[] data = new boolean[]{true, false, true, true, false, true, false, false, true, false};
        UsedLanes lanes = new UsedLanes(createBits(data));

        assertEquals(10, lanes.size());
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
    void isUsedOutOfRangeTest() {
        final boolean[] data = new boolean[]{true, false, true, true, false, true, false, false, true, false};
        UsedLanes lanes1 = new UsedLanes(createBits(data));
        for (int x = lanes1.size(); x < 100; x++) {
            assertEquals(false, lanes1.isUsed(x));
        }
        assertEquals(false, lanes1.isUsed(1000));
        assertEquals(false, lanes1.isUsed(10000));
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
    void tenLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{true, true, true, true, true, true, true, true, true, true}));
        assertEquals("UsedLanes(1111111111)", lanes.toString());
    }

    @Test
    void nineLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{true, true, true, true, true, true, true, true, true, false}));
        assertEquals("UsedLanes(1111111110)",
                     lanes.toString());
    }

    @Test
    void eightLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, true, true, true, true, true, true, true, true, false}));
        assertEquals("UsedLanes(0111111110)",
                     lanes.toString());
    }

    @Test
    void sevenLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, true, true, true, true, true, true, true, false, false}));
        assertEquals("UsedLanes(0111111100)",
                     lanes.toString());
    }

    @Test
    void sixLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, true, true, true, true, true, true, false, false}));
        assertEquals("UsedLanes(0011111100)",
                     lanes.toString());
    }

    @Test
    void fiveLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, true, true, true, true, true, false, false, false}));
        assertEquals("UsedLanes(0011111000)",
                     lanes.toString());
    }

    @Test
    void fourLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, true, true, true, true, false, false, false}));
        assertEquals("UsedLanes(0001111000)",
                     lanes.toString());
    }

    @Test
    void threeLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, true, true, true, false, false, false, false}));
        assertEquals("UsedLanes(0001110000)",
                     lanes.toString());
    }

    @Test
    void twoLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, true, true, false, false, false, false}));
        assertEquals("UsedLanes(0000110000)",
                     lanes.toString());
    }

    @Test
    void oneLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, true, false, false, false, false, false}));
        assertEquals("UsedLanes(0000100000)",
                     lanes.toString());
    }

    @Test
    void zeroLanesUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, false, false, false, false, false, false}));
        assertEquals("UsedLanes(0000000000)",
                     lanes.toString());
    }

    @Test
    void zeroLanesUsedEmptyTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{}));
        assertEquals("UsedLanes(0000000000)",
                     lanes.toString());
    }

    @Test
    void oneLanesUsedPartialTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{false, false, false, false, true, false, false, false}));
        assertEquals("UsedLanes(0000100000)",
                     lanes.toString());
    }

    @Test
    void zeroLanesPartialUsedTest() {
        UsedLanes lanes = new UsedLanes(createBits(new boolean[]{}));
        assertEquals("UsedLanes(0000000000)",
                     lanes.toString());
    }

    @Test
    void toValue() {
        final boolean[] data = new boolean[]{true, false, true, true, false, true, false, false, true, false};
        UsedLanes lanes = new UsedLanes(createBits(data));

        String actual = lanes.toValue();

        assertEquals("1011010010", actual);
    }

    @Test
    void fromValue() {
        final boolean[] data = new boolean[]{true, false, true, true, false, true, false, false, true, false};
        UsedLanes expected = new UsedLanes(createBits(data));

        UsedLanes actual = UsedLanes.fromValue("1011010010");

        assertEquals(expected, actual);
    }

    @Test
    void fromValueWrongLength() {
        assertThrows(IllegalArgumentException.class, () -> UsedLanes.fromValue("101101001"));
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'b', 'c', 'x', 'y', 'z', '+', '-', '*', '#'})
    void fromValueWrongCharacter(char wrongChar) {
        assertThrows(IllegalArgumentException.class, () -> UsedLanes.fromValue("011010010" + wrongChar));
    }
}
