package org.lisasp.alphatimer.heats.service;

import org.lisasp.alphatimer.heats.entity.HeatEntity;
import org.springframework.data.repository.CrudRepository;

public interface HeatJpaRepository extends CrudRepository<HeatEntity, String>, HeatRepository {
}
