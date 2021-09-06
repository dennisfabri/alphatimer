package org.lisasp.alphatimer.heats.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lisasp.alphatimer.heats.api.enums.LaneStatus;
import org.lisasp.alphatimer.heats.api.enums.Penalty;
import org.lisasp.alphatimer.heats.api.LaneDto;
import org.lisasp.alphatimer.spring.jpa.VersionedBaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "Lane")
@Table(name="lane")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaneEntity extends VersionedBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heatId")
    private HeatEntity heat;
    @Min(0)
    private int number;
    @Min(0)
    private int timeInMillis;
    @NotNull
    @Enumerated(EnumType.STRING)
    private LaneStatus status;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Penalty penalty;
    @Min(0)
    private int lap;

    public LaneDto toDto() {
        return new LaneDto(number, timeInMillis, status, penalty, lap);
    }
}
