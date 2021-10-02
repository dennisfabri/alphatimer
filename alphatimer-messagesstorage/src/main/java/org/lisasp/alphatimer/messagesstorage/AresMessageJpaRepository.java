package org.lisasp.alphatimer.messagesstorage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AresMessageJpaRepository extends JpaRepository<AresMessage, String>, AresMessageRepository {
}
