package org.lisasp.alphatimer.heats.api;

import lombok.Value;
import org.lisasp.alphatimer.heats.api.enums.LaneStatus;
import org.lisasp.alphatimer.heats.api.enums.Penalty;

@Value
public class LaneDto {
    private final int number;
    private final int timeInMillis;
    private final LaneStatus status;
    private final Penalty penalty;
    private final int lap;
}
