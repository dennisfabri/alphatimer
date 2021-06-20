package org.lisasp.alphatimer.api.protocol;

public interface DataHandlingMessageAggregator extends DataInputEventListener {
    void register(DataHandlingMessageListener listener);
}
