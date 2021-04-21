package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.events.messages.Ping;
import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;

public class DataHandlingMessageAggregator implements DataListener {

    private DataHandlingMessage1 message1 = null;

    private List<DataListener> listeners = new ArrayList<>();

    @Synchronized("listeners")
    public void register(DataListener listener) {
        listeners.add(listener);
    }

    private void notify(DataHandlingMessage message) {
        listeners.forEach(l -> l.notify(message));
    }

    @Override
    @Synchronized("listeners")
    public void notify(DataInputEvent event) {
        if (event instanceof DataHandlingMessage1) {
            message1 = (DataHandlingMessage1) event;
            return;
        }
        if (event instanceof DataHandlingMessage2) {
            if (message1 != null) {
                DataHandlingMessage2 message2 = (DataHandlingMessage2) event;
                notify(new DataHandlingMessage(message1.getMessageType(),
                                               message1.getKindOfTime(),
                                               message1.getTimeType(),
                                               message1.getUsedLanes(),
                                               message1.getLapCount(),
                                               message1.getEvent(),
                                               message1.getHeat(),
                                               message1.getRank(),
                                               message1.getRankInfo(),
                                               message2.getLane(),
                                               message2.getCurrentLap(),
                                               message2.getTimeInMillis(),
                                               message2.getTimeInfo(),
                                               message2.getTimeMarker()));
            }
        }
        if (event instanceof Ping) {
            // Do not reset message1 on ping
            return;
        }
        message1 = null;
    }
}
