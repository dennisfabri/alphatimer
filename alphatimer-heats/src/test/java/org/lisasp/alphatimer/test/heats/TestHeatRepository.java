package org.lisasp.alphatimer.test.heats;

import com.rits.cloning.Cloner;
import org.lisasp.alphatimer.heats.entity.HeatEntity;
import org.lisasp.alphatimer.heats.service.HeatRepository;
import org.lisasp.basics.spring.jpa.BaseEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestHeatRepository implements HeatRepository {

    private List<HeatEntity> entities = new ArrayList<>();
    private Cloner cloner = new Cloner();

    public TestHeatRepository() {
        cloner.registerImmutable(LocalDateTime.class);
    }

    @Override
    public Optional<HeatEntity> findByCompetitionAndEventAndHeat(String competition, int event, int heat) {
        return entities.stream().filter(e -> e.getCompetition().equals(competition) && e.getEvent() == event && e.getHeat() == heat).map(e -> cloner.deepClone(e)).findFirst();
    }

    @Override
    public long count() {
        return entities.size();
    }

    @Override
    public <S extends HeatEntity> S save(S s) {
        beforeSave(s);
        S entity = cloner.deepClone(s);
        entities.removeIf(e -> e.getId() == entity.getId());
        entities.add(entity);
        return cloner.deepClone(entity);
    }

    private <S extends HeatEntity> void beforeSave(S s) {
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
