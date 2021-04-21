package de.dennisfabri.alphatimer.api;

import de.dennisfabri.alphatimer.api.events.DataInputEvent;

public interface DataListener {
    void notify(DataInputEvent event);
}
