package org.lisasp.alphatimer.heats.current.service;

import org.lisasp.alphatimer.heats.current.entity.LaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentLaneRepository extends JpaRepository<LaneEntity, String> {
    List<LaneEntity> findAllByCompetitionAndEventAndHeat(String competition, int event, int heat);
}
