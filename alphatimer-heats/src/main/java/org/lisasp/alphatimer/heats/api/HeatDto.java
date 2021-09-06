package org.lisasp.alphatimer.heats.api;

import lombok.Value;
import org.lisasp.alphatimer.heats.api.enums.HeatStatus;

import java.time.LocalDateTime;

@Value
public class HeatDto {

    private final String competition;
    private final int event;
    private final int heat;
    private final HeatStatus status;
    private final LocalDateTime started;
    private final int lapCount;
    private final LaneDto[] lanes;
}
