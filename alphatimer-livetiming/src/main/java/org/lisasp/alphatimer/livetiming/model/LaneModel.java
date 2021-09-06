package org.lisasp.alphatimer.livetiming.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.lisasp.alphatimer.heats.api.LaneDto;

@Value
@Getter
@RequiredArgsConstructor
public class LaneModel {
    private final int number;
    private final String name;
    private final String event;
    private final String heat;
    private final String time;
    private final String status;
    private final int lap;
    private final int lapCount;

    public LaneModel(int event, int heat, int lapCount, LaneDto lane) {
        number = lane.getNumber();
        this.event = "" + event;
        this.heat = "" + heat;
        name = "";
        lap = lane.getLap();
        this.lapCount = lapCount;
        switch (lane.getStatus()) {
            case Used:
                time = formatTime(lane.getTimeInMillis());
                break;
            default:
                time = "";
        }
        switch (lane.getPenalty()) {
            case DidNotStart:
                status = "D.N.S.";
                break;
            case DidNotFinish:
                status = "D.N.F.";
                break;
            default:
                if (lap > 0 && lapCount > 0 && lap < lapCount) {
                    status = String.format("%d/%d", lap, lapCount);
                } else {
                    status = "";
                }
                break;
        }
    }

    private static String formatTime(int timeInMillis) {
        if (timeInMillis <= 0) {
            return "--:--,--";
        }
        int minutes = timeInMillis / 60 / 1000;
        double seconds = 0.001 * (timeInMillis % (60 * 1000));

        return String.format("%02d:%05.2f", minutes, seconds);
    }
}
