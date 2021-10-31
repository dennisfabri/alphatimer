package org.lisasp.alphatimer.api.ares.serial;

public interface DataHandlingMessageAggregator extends DataInputEventListener {
    void register(DataHandlingMessageListener listener);
}
