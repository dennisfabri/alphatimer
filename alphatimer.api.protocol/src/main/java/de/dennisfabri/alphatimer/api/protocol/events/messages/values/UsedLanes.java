package de.dennisfabri.alphatimer.api.protocol.events.messages.values;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.BitSet;

@EqualsAndHashCode
@ToString
public class UsedLanes {

    private final boolean[] lanes;

    public UsedLanes(BitSet lanes) {
        this.lanes = new boolean[10];
        for (int pos = 0; pos < Math.min(this.lanes.length, lanes.length()); pos++) {
            this.lanes[pos] = lanes.get(pos);
        }
    }

    public boolean isUsed(int x) {
        return lanes[x];
    }
}
