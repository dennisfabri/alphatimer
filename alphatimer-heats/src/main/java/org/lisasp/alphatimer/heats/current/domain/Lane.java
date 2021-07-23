package org.lisasp.alphatimer.heats.current.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.lisasp.alphatimer.heats.api.LaneStatus;
import org.lisasp.alphatimer.heats.current.api.LaneDto;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Lane {

    private final int number;

    private int timeInMillis;
    private LaneStatus status = LaneStatus.NotUsed;

    public Lane(LaneDto lane) {
        this.number = lane.getNumber();
        this.timeInMillis = lane.getTimeInMillis();
        this.status = lane.getStatus();
    }

    public LaneDto createDto() {
        return new LaneDto(number, timeInMillis, status);
    }

    public void finish() {
        status = status.finish();
    }
}
