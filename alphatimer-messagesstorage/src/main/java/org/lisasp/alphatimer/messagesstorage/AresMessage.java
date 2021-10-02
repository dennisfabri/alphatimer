package org.lisasp.alphatimer.messagesstorage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.basics.spring.jpa.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity()
@Getter
@Table(name = "aresmessage")
@NoArgsConstructor
@AllArgsConstructor
public class AresMessage extends BaseEntity {
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
    @Min(0)
    private byte lapCount;
    @Min(0)
    private short event;
    @Min(0)
    private byte heat;
    @Min(0)
    private byte rank;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RankInfo rankInfo;
    @Min(0)
    private byte lane;
    @Min(0)
    private byte currentLap;
    @Min(0)
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
