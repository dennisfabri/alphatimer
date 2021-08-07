package org.lisasp.alphatimer.heats.current.entity;

import javax.persistence.*;

import lombok.*;
import org.lisasp.alphatimer.heats.api.HeatStatus;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.api.LaneDto;
import org.lisasp.alphatimer.spring.jpa.AbstractBaseEntity;

import java.time.LocalDateTime;

@Entity(name = "Heat")
@Table(name="heat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeatEntity extends AbstractBaseEntity {

    private String competition;
    private int event;
    private int heat;
    @Enumerated(EnumType.STRING)
    private HeatStatus status;
    private LocalDateTime started;

    public HeatDto toDto(LaneDto[] lanes) {
        return new HeatDto(competition, event, heat, status, started, lanes);
    }

    public void update(HeatDto heat) {
        started = heat.getStarted();
    }
}
