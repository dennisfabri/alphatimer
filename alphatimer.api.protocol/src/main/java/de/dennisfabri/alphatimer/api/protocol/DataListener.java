package de.dennisfabri.alphatimer.api.protocol;

import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;

public interface DataListener {
    void notify(DataInputEvent event);
}
