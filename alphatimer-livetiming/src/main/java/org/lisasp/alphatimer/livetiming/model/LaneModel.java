package org.lisasp.alphatimer.livetiming.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.lisasp.alphatimer.heats.current.api.LaneDto;

@Value
@Getter
@RequiredArgsConstructor
public class LaneModel {
    private final int number;
    private final String name;
    private final String time;
    private final String status;

    public LaneModel(LaneDto lane) {
        number = lane.getNumber();
        name = "";
        switch (lane.getStatus()) {
            case Used:
                time = formatTime(0);
                status = "";
                break;
            case DidNotFinish:
                time = formatTime(0);
                status = "D.N.F.";
                break;
            case DidNotStart:
                time = formatTime(0);
                status = "D.N.S.";
                break;
            case Started:
                time = formatTime(lane.getTimeInMillis());
                status = "";
                break;
            case Finished:
                time = formatTime(lane.getTimeInMillis());
                status = "";
                break;
            default:
                time = "";
                status = "";
        }
    }

    private static String formatTime(int timeInMillis) {
        if (timeInMillis <= 0) {
            return "--:--,--";
        }
        return "-" + timeInMillis;
    }
}
