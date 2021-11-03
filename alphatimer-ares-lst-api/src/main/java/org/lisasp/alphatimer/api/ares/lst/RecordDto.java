package org.lisasp.alphatimer.api.ares.lst;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordDto {
    private int distanceId;
    private int styleId;
    private int recId;
    private String sex;
    private int timeInHundreds;
    private String name;
    private LocalDate date;
    private String place;
}
