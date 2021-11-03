package org.lisasp.alphatimer.api.ares.lst;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoundDto {
    private int id;
    private String title;
    private String shortTitle;
    private String rountText;
    private EnumerationType enumerationType;
    private int amount;
}
