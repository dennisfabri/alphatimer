package org.lisasp.alphatimer.heats.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.TimeMessage;
import org.lisasp.alphatimer.heats.api.LaneDto;
import org.lisasp.alphatimer.heats.api.enums.LaneStatus;
import org.lisasp.alphatimer.heats.api.enums.Penalty;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Lane {

    @Getter
    private final int number;

    private int timeInMillis;
    private LaneStatus status = LaneStatus.NotUsed;
    private Penalty penalty = Penalty.None;
    private int lap;

    public Lane(LaneDto lane) {
        this.number = lane.getNumber();
        this.timeInMillis = lane.getTimeInMillis();
        this.status = lane.getStatus();
        this.penalty = lane.getPenalty();
        this.lap = lane.getLap();

        assureTimeFitsStatus();
    }

    public LaneDto createDto() {
        return new LaneDto(number, timeInMillis, status, penalty, lap);
    }

    public void finish() {
        assureTimeFitsStatus();
    }

    public void start(StartMessage message) {
        timeInMillis = 0;
        lap = 0;

        assureTimeFitsStatus();
    }

    public void touch(TimeMessage message) {
        if (message.getTimeInMillis() <= 0) {
            return;
        }
        timeInMillis = message.getTimeInMillis();
        lap = message.getCurrentLap();

        assureTimeFitsStatus();
    }

    public void used(boolean used) {
        if (used) {
            status = LaneStatus.Used;
        } else {
            status = LaneStatus.NotUsed;
        }

        assureTimeFitsStatus();
    }

    private void assureTimeFitsStatus() {
        if (!status.mayHaveTime()) {
            timeInMillis = 0;
            lap = 0;
        }
        if (timeInMillis <= 0) {
            timeInMillis = 0;
            lap = 0;
        }
    }
}
