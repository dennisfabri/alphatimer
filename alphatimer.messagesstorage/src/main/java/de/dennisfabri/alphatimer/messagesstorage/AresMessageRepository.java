package de.dennisfabri.alphatimer.messagesstorage;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AresMessageRepository extends CrudRepository<AresMessage, String> {
    List<AresMessage> findAllByEventAndHeat(short event, byte heat);
}
