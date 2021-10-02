package org.lisasp.alphatimer.test.datatests;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HitCounter {

    private HashMap<Class<?>, Integer> messageHitCount = new HashMap<>();

    void increaseHitCount(Class<?> clazz) {
        messageHitCount.putIfAbsent(clazz, 0);
        messageHitCount.compute(clazz, (key, count) -> ++count);
    }

    int getHitCount(Class<?> clazz) {
        int count = messageHitCount.getOrDefault(clazz, 0);
        messageHitCount.remove(clazz);
        return count;
    }

    void noMoreHits() {
        assertTrue(messageHitCount.isEmpty());
    }
}
