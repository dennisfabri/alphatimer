package de.dennisfabri.alphatimer.legacy.model;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;

@ToString
@EqualsAndHashCode
public class Heat implements Serializable {

    private static final long serialVersionUID = 4925782095479969005L;

    private final HashMap<Integer, Lane> lanes;
    @XStreamAsAttribute
    private final int event;
    @XStreamAsAttribute
    private final int heat;
    @XStreamAsAttribute
    private final String id;

    public Heat(String id, int event, int heatname) {
        lanes = new HashMap<>();
        this.event = event;
        this.heat = heatname;
        this.id = id;
    }

    public void store(int lanenr, long time, LaneStatus status) {
        synchronized (lanes) {
            for (int i = 0; i <= lanenr; i++) {
                lanes.computeIfAbsent(i, j -> new Lane(j));
            }
            lanes.computeIfAbsent(lanenr, j -> new Lane(j)).store(time, status);
        }
    }

    public int getHeat() {
        return heat;
    }

    public int getEvent() {
        return event;
    }
}
