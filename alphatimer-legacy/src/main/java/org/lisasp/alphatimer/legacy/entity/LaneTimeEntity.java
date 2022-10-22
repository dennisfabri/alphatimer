package org.lisasp.alphatimer.legacy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lisasp.alphatimer.legacy.dto.LaneStatus;
import org.lisasp.basics.spring.jpa.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lanetime")
public class LaneTimeEntity
        extends BaseEntity
        implements Comparable<LaneTimeEntity> {

    @NotNull
    private String competition;
    @Min(1)
    private int event;
    @Min(1)
    private int heatNumber;
    @Min(0)
    private int laneIndex;
    @Min(0)
    private long timeInMillis;
    @NotNull
    @Enumerated(EnumType.STRING)
    private LaneStatus status;
    @NotNull
    private LocalDateTime timestamp;

    @Override
    public int compareTo(LaneTimeEntity o) {
        if (o == null) {
            return 1;
        }

        if (!competition.equals(o.getCompetition())) {
            return competition.compareTo(o.getCompetition());
        }
        if (event != o.getEvent()) {
            return event - o.getEvent();
        }
        if (heatNumber != o.getHeatNumber()) {
            return heatNumber - o.getHeatNumber();
        }

        return getTimestamp().compareTo(o.getTimestamp());
    }
}
