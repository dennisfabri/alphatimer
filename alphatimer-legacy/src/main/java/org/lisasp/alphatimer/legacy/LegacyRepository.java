package org.lisasp.alphatimer.legacy;

import org.lisasp.alphatimer.legacy.entity.LaneTimeEntity;

import java.util.List;
import java.util.Optional;

public interface LegacyRepository {

    List<LaneTimeEntity> findAllByCompetition(String competition);

    Optional<LaneTimeEntity> findTop1ByOrderByTimestampDesc();

    <S extends LaneTimeEntity> S save(S entity);
}
