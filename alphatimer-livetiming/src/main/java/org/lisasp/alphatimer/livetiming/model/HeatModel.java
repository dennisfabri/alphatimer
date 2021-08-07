package org.lisasp.alphatimer.livetiming.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.lisasp.alphatimer.heats.current.api.HeatDto;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class HeatModel {
    private final String name;
    private final LocalDateTime started;

    public HeatModel(HeatDto dto) {
        this(dto.createName(), dto.getStarted());
    }
}
