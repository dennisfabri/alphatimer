package org.lisasp.alphatimer.heats.current.api;

import lombok.Value;
import org.lisasp.alphatimer.heats.api.HeatStatus;

import java.time.LocalDateTime;

@Value
public class HeatDto {

    private String competition;
    private int event;
    private int heat;
    private HeatStatus status;
    private LocalDateTime started;
    private LaneDto[] lanes;

    public String createName() {
        return String.format("%02d-%03d", event, heat);
    }
}
