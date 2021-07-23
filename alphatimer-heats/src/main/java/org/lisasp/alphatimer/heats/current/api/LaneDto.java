package org.lisasp.alphatimer.heats.current.api;

import lombok.Value;
import org.lisasp.alphatimer.heats.api.LaneStatus;

@Value
public class LaneDto {
    private int number;
    private int timeInMillis;
    private LaneStatus status;
}
