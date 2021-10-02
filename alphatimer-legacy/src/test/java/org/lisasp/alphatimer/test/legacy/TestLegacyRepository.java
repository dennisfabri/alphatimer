package org.lisasp.alphatimer.test.legacy;

import com.rits.cloning.Cloner;
import org.lisasp.alphatimer.legacy.LegacyRepository;
import org.lisasp.alphatimer.legacy.entity.LaneTimeEntity;
import org.lisasp.basics.spring.jpa.BaseEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestLegacyRepository implements LegacyRepository {

    private List<LaneTimeEntity> entities = new ArrayList<>();
    private Cloner cloner = new Cloner();

    public TestLegacyRepository() {
        cloner.registerImmutable(LocalDateTime.class);
    }

    @Override
    public List<LaneTimeEntity> findAllByCompetition(String competition) {
        return entities.stream().filter(e -> e.getCompetition().equals(competition)).map(e -> cloner.deepClone(e)).collect(Collectors.toList());
    }

    @Override
    public Optional<LaneTimeEntity> findTop1ByOrderByTimestampDesc() {
        return entities.stream().sorted(Comparator.comparing(LaneTimeEntity::getTimestamp)).sorted(Comparator.reverseOrder()).limit(1).map(e -> cloner.deepClone(
                e)).findFirst();
    }

    @Override
    public <S extends LaneTimeEntity> S save(S s) {
        beforeSave(s);
        S entity = cloner.deepClone(s);
        entities.removeIf(e -> e.getId() == entity.getId());
        entities.add(entity);
        return cloner.deepClone(entity);
    }

    private <S extends LaneTimeEntity> void beforeSave(S s) {
        try {
            Class<?> entityClass = BaseEntity.class;
            Method[] methods = entityClass.getDeclaredMethods();
            Method method = entityClass.getDeclaredMethod("beforeSave");
            method.setAccessible(true);
            method.invoke(s);
            method.setAccessible(false);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
