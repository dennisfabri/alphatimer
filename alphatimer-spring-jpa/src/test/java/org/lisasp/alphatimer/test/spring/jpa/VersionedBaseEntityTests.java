package org.lisasp.alphatimer.test.spring.jpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VersionedBaseEntityTests {

    @Autowired
    private TestEntityRepository repository;

    @Test
    void empty() {
    }

    @Test
    void construct() {
        TestEntity entity = new TestEntity("test");

        assertTrue(entity.isNew());
        assertTrue(entity.getId().length() > 10);
    }

    @Test
    void timestamp() {
        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        TestEntity entity = new TestEntity("test");

        repository.save(entity);
        TestEntity actual = repository.findById(entity.getId()).get();

        LocalDateTime after = LocalDateTime.now(ZoneOffset.UTC);

        assertTrue(before.isBefore(entity.getLastModification()) || before.isEqual(entity.getLastModification()));
        assertTrue(after.isAfter(entity.getLastModification()) || after.isEqual(entity.getLastModification()));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 13})
    void versionValue(int amount) {
        TestEntity entity = new TestEntity();

        for (int x = 1; x <= amount; x++) {
            entity.setName("test" + x);
            repository.saveAndFlush(entity);
        }

        List<TestEntity> actualValues = repository.findAllByName("test" + amount);
        TestEntity actual = actualValues.get(0);

        assertEquals(1, actualValues.size());
        assertEquals(entity, actual);

        assertEquals(amount, actual.getVersion());
    }

    @Test
    void versionValue2() {
        TestEntity entity = new TestEntity("test1");

        repository.save(entity);
        entity.setName("test2");

        List<TestEntity> actual = repository.findAllByName("test2");

        assertEquals(2, entity.getVersion());
    }

    @Test
    void checkIntegrity() {
        TestEntity entity = new TestEntity("test1");

        repository.save(entity);
        entity.setName("test2");
        repository.save(entity);

        List<TestEntity> actual = repository.findAllByName("test2");

        long size = repository.count();
        assertEquals(1, size);

        assertEquals(1, actual.size());
        assertEquals(entity.getName(), actual.get(0).getName());
        assertEquals(entity.getId(), actual.get(0).getId());
        assertEquals(entity.getVersion(), actual.get(0).getVersion());
        assertTrue(entity.getLastModification().isBefore(actual.get(0).getLastModification()) || entity.getLastModification().isEqual(actual.get(0).getLastModification()));
    }
}
