package org.lisasp.alphatimer.test.messagesstorage;

import com.rits.cloning.Cloner;
import org.lisasp.alphatimer.messagesstorage.AresMessage;
import org.lisasp.alphatimer.messagesstorage.AresMessageRepository;
import org.lisasp.basics.spring.jpa.BaseEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestAresMessageRepository implements AresMessageRepository {

    private List<AresMessage> entities = new ArrayList<>();
    private Cloner cloner = new Cloner();

    public TestAresMessageRepository() {
        cloner.registerImmutable(LocalDateTime.class);
    }

    @Override
    public List<AresMessage> findAllByCompetitionKeyAndEventAndHeat(String competitionKey, short event, byte heat) {
        return entities.stream().filter(e -> e.getCompetitionKey().equals(competitionKey) && e.getEvent() == event && e.getHeat() == heat).map(e -> cloner.deepClone(
                e)).collect(Collectors.toList());
    }

    @Override
    public <S extends AresMessage> S save(S s) {
        beforeSave(s);
        S entity = cloner.deepClone(s);
        entities.removeIf(e -> e.getId() == entity.getId());
        entities.add(entity);
        return cloner.deepClone(entity);
    }

    @Override
    public long count() {
        return entities.size();
    }

    private <S extends AresMessage> void beforeSave(S s) {
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
