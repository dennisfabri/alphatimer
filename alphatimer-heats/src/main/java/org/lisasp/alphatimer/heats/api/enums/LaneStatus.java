package org.lisasp.alphatimer.heats.api.enums;

public enum LaneStatus {
    NotUsed,
    Used;

    public boolean mayHaveTime() {
        return this.equals(Used);
    }
}
