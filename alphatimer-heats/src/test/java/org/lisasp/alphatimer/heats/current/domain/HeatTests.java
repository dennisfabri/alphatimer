package org.lisasp.alphatimer.heats.current.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeatTests {

    private Heat heat;

    private static Heat createHeat() {
        return new Heat("test", 13, 4);
    }

    @BeforeEach
    void prepare() {
        heat = createHeat();
    }

    @Test
    void initialize() {
        assertEquals(heat, heat);
    }
}
