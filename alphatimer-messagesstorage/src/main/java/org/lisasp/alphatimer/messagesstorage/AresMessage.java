package org.lisasp.alphatimer.messagesstorage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.alphatimer.spring.jpa.AbstractBaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "aresmessage")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AresMessage extends AbstractBaseEntity {
    @Column(nullable = false)
    private String originalText1;
    @Column(nullable = false)
    private String originalText2;
    @Column(nullable = false)
    private LocalDateTime timestamp;
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
    @Column(nullable = false)
    private String competitionKey;
}
