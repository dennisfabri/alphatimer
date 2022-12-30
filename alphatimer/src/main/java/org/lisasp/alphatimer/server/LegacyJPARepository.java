package org.lisasp.alphatimer.server;

import org.lisasp.alphatimer.legacy.LegacyRepository;
import org.lisasp.alphatimer.legacy.entity.LaneTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LegacyJPARepository extends JpaRepository<LaneTimeEntity, String>, LegacyRepository {
}
