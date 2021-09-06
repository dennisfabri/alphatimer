package org.lisasp.alphatimer.legacy;

import org.lisasp.alphatimer.legacy.entity.LaneTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LegacyRepository extends JpaRepository<LaneTimeEntity, String> {
    List<LaneTimeEntity> findAllByCompetition(String competition);

    Optional<LaneTimeEntity> findTop1ByOrderByTimestampDesc();
}
