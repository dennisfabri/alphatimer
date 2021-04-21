package de.dennisfabri.alphatimer.legacy.model;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Arrays;

@EqualsAndHashCode
public class Lane implements Serializable {

    private static final long serialVersionUID = -7389645818914972121L;

    @XStreamAsAttribute
    private final int laneindex;

    private long[] times = new long[0];

    private LaneStatus[] stati = new LaneStatus[0];

    public Lane(int index) {
        laneindex = index;
    }

    public void store(long time, LaneStatus status) {
        synchronized (this) {
            long[] newtimes = Arrays.copyOf(times, times.length + 1);
            LaneStatus[] newstati = Arrays.copyOf(stati, stati.length + 1);
            newtimes[times.length] = time;
            newstati[stati.length] = status;
            times = newtimes;
            stati = newstati;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(laneindex);
        sb.append(":");
        for (int x = 0; x < times.length; x++) {
            long time = times[x];
            LaneStatus status = stati[x];

            sb.append(" ");
            sb.append(zeitString(time));
            sb.append(" (");
            sb.append(status);
            sb.append(")");
        }
        return sb.toString();
    }

    private static String zeitString(final long zeit) {
        long min = zeit / 60000;
        int sec = (int) ((zeit % 60000) / 1000);
        int millis = (int) (zeit % 1000);
        StringBuilder s = new StringBuilder();
        s.append(min);
        s.append(':');
        if (sec < 10) {
            s.append('0');
        }
        s.append(sec);
        s.append(',');
        if (millis < 100) {
            s.append('0');
        }
        if (millis < 10) {
            s.append('0');
        }
        s.append(millis);
        return s.toString();
    }
}
