package org.lisasp.alphatimer.heats.service;

import org.lisasp.alphatimer.heats.entity.HeatEntity;

import java.util.List;
import java.util.Optional;

public interface HeatRepository {
    Optional<HeatEntity> findByCompetitionAndEventAndHeat(String competition, int event, int heat);

    long count();

    <S extends HeatEntity> S save(S s);
}
