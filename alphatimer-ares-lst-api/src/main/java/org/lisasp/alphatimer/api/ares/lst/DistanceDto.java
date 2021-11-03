package org.lisasp.alphatimer.api.ares.lst;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistanceDto {
    private int id;
    private int length;
    private int relayChanges;
}
