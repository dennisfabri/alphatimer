package org.lisasp.alphatimer.api.ares.lst;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RaceDto {
    private int eventId;
    private int roundId;
    private int heatNumber;
    private int distanceId;
    private int styleId;
    private String sex;
    private LocalDateTime schedule;
}
