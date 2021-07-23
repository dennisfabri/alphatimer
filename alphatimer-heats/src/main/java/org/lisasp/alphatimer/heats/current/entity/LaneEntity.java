package org.lisasp.alphatimer.heats.current.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lisasp.alphatimer.heats.api.LaneStatus;
import org.lisasp.alphatimer.heats.current.api.LaneDto;
import org.lisasp.alphatimer.spring.jpa.AbstractBaseEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaneEntity extends AbstractBaseEntity {
    private String competition;
    private int event;
    private int heat;
    private int number;
    private int timeInMillis;
    private LaneStatus status;

    public LaneDto toDto() {
        return new LaneDto(number, timeInMillis, status);
    }
}
