package org.lisasp.alphatimer.livetiming.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.lisasp.alphatimer.heats.api.HeatDto;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class HeatModel {
    private final String name;
    private final String event;
    private final String heat;
    private final LocalDateTime started;

    public HeatModel(HeatDto dto) {
        this(createName(dto), "" + dto.getEvent(), "" + dto.getHeat(), dto.getStarted());
    }

    private static String createName(HeatDto dto) {
        return String.format("%02d-%03d", dto.getEvent(), dto.getHeat());
    }
}
