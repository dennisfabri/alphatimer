package org.lisasp.alphatimer.api.protocol;

public interface ByteConsumer extends AutoCloseable {
    void accept(byte entry);

    void register(DataInputEventListener listener);
}
