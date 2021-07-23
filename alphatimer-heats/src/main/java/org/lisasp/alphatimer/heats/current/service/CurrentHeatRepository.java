package org.lisasp.alphatimer.heats.current.service;

import org.lisasp.alphatimer.heats.current.entity.HeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentHeatRepository extends JpaRepository<HeatEntity, String> {
    Optional<HeatEntity> findByCompetitionAndEventAndHeat(String competition, int event, int heat);
}
