package org.lisasp.alphatimer.heats.service;

import org.lisasp.alphatimer.heats.entity.HeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeatRepository extends JpaRepository<HeatEntity, String> {
    Optional<HeatEntity> findByCompetitionAndEventAndHeat(String competition, int event, int heat);
}
