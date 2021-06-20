package org.lisasp.alphatimer.livetiming.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@RequiredArgsConstructor
public class Lane {
    private final int number;
    private int time;
    private LaneStatus status;
}
