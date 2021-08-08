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
                // time = formatTime(0);
                time = "D.N.F.";
                status = "";
                break;
            case DidNotStart:
                // time = formatTime(0);
                time = "D.N.S.";
                status = "";
                break;
            case Started:
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
        int minutes = timeInMillis / 60 / 1000 ;
        double seconds = 0.001 * (timeInMillis % (60* 1000));

        return String.format("%02d:%05.2f", minutes, seconds);
    }
}
