package org.lisasp.alphatimer.heats.current.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.lisasp.alphatimer.api.refinedmessages.accepted.TimeMessage;
import org.lisasp.alphatimer.heats.api.LaneStatus;
import org.lisasp.alphatimer.heats.current.api.LaneDto;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Lane {

    @Getter
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

    public void apply(TimeMessage message) {
        status = status.timeUpdate();
        timeInMillis = message.getTimeInMillis();

        assureTimeFitsStatus();
    }

    public void used(boolean used) {
        if (used) {
            status = status.used();
        } else {
            status = status.unused();
        }

        assureTimeFitsStatus();
    }

    private void assureTimeFitsStatus() {
        if (!status.mayHaveTime()) {
            timeInMillis = 0;
        }
    }
}
