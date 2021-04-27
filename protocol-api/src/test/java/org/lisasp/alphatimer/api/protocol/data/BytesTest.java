package org.lisasp.alphatimer.api.protocol.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BytesTest {

    private Bytes bytes;

    @BeforeEach
    void prepare() {
        bytes = new Bytes(new byte[]{0, 1, 2, 4, 7});
    }

    @Test
    void get() {
        assertEquals(0, bytes.get(0));
        assertEquals(1, bytes.get(1));
        assertEquals(2, bytes.get(2));
        assertEquals(4, bytes.get(3));
        assertEquals(7, bytes.get(4));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -15, -100})
    void getNegativeIndex(int index) {
        assertThrows(IndexOutOfBoundsException.class, () -> bytes.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 19, 1000})
    void getOutOfBounds(int index) {
        assertThrows(IndexOutOfBoundsException.class, () -> bytes.get(index));
    }
}
