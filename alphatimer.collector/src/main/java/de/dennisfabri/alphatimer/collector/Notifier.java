package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.events.DataInputEvent;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;

class Notifier {

    private final List<DataListener> dataListeners = new ArrayList<>();

    @Synchronized("dataListeners")
    void notify(DataInputEvent event) {
        dataListeners.forEach(dl -> dl.notify(event));
    }

    @Synchronized("dataListeners")
    void register(DataListener dataListener) {
        dataListeners.add(dataListener);
    }
}
