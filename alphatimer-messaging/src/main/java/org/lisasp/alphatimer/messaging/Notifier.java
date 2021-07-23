package org.lisasp.alphatimer.messaging;

import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Notifier<T> implements Consumer<T> {

    protected final List<Consumer<T>> dataListeners = new ArrayList<>();

    @Synchronized("dataListeners")
    public void accept(T event) {
        dataListeners.forEach(dl -> dl.accept(event));
    }

    @Synchronized("dataListeners")
    public void register(Consumer<T> dataListener) {
        dataListeners.add(dataListener);
    }
}
