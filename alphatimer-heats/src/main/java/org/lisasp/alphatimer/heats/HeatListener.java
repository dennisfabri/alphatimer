package org.lisasp.alphatimer.heats;

import org.lisasp.alphatimer.heats.api.Heat;
import org.lisasp.alphatimer.heats.current.api.HeatDto;

import java.util.function.Consumer;

public interface HeatListener extends Consumer<HeatDto> {
}
