package org.lisasp.alphatimer.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectingConsumer<T> implements Consumer<T> {

    private List<T> accepted = new ArrayList<>();

    @Override
    public void accept(T entry) {
        accepted.add(entry);
    }

    public void forEach(Consumer<? super T> consumer) {
        accepted.forEach(consumer);
    }

    public boolean isEmpty() {
        return accepted.isEmpty();
    }
}
