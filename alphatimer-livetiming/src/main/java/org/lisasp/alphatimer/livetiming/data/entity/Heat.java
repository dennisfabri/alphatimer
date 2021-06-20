package org.lisasp.alphatimer.livetiming.data.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.lisasp.alphatimer.livetiming.data.AbstractEntity;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Heat extends AbstractEntity {

    private Integer event;
    private Integer heat;

    public String getName() {
        return String.format("%02d-%03d", event, heat);
    }
}
