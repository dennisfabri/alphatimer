package org.lisasp.alphatimer.test.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

public interface TestEntityRepository extends JpaRepository<TestEntity, String> {
    List<TestEntity> findAllByName(String name);
}
