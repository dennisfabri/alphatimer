package org.lisasp.alphatimer.heats;

import org.lisasp.alphatimer.heats.api.HeatDto;

import java.util.function.Consumer;

public interface HeatListener extends Consumer<HeatDto> {
}
