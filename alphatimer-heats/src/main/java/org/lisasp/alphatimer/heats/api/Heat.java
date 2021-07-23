package org.lisasp.alphatimer.heats.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Heat {
    
    private final String competition;
    private final short event;
    private final byte heat;

    private HeatStatus status = HeatStatus.Open;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime lastUpdateTime;

    public Heat start(LocalDateTime timestamp) {
        status = HeatStatus.Started;
        if (startTime == null) {
            startTime = timestamp;
        }
        lastUpdateTime = timestamp;
        return this;
    }

    public Heat finish(LocalDateTime timestamp) {
        start(timestamp);
        status = HeatStatus.Finished;
        endTime = timestamp;
        lastUpdateTime = timestamp;
        return this;
    }

    public Heat copy() {
        Heat copy = new Heat(competition, event, heat);
        copy.status = status;
        copy.startTime = startTime;
        copy.endTime = endTime;
        copy.lastUpdateTime = lastUpdateTime;
        return copy;
    }

    public void trigger(LocalDateTime timestamp) {
        lastUpdateTime = timestamp;
    }
}
