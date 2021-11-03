package org.lisasp.alphatimer.api.ares.lst;

import lombok.Data;

@Data
public class StartDto {
    private int eventId;
    private int roundId;
    private int heatId;
    private int laneNumber;
    private int indexInRelay;
    private int competitorId;
}
