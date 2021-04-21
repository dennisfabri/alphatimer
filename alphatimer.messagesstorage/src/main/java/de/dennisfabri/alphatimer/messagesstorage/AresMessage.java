package de.dennisfabri.alphatimer.messagesstorage;

import de.dennisfabri.alphatimer.api.events.messages.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AresMessage extends AbstractBaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KindOfTime kindOfTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeType timeType;
    @Column(nullable = false)
    private String usedLanes;
    private byte lapCount;
    private short event;
    private byte heat;
    private byte rank;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RankInfo rankInfo;
    private byte lane;
    private byte currentLap;
    private int timeInMillis;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeInfo timeInfo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeMarker timeMarker;
}
