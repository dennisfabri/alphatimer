package org.lisasp.alphatimer.api.protocol;

import org.lisasp.alphatimer.messaging.ByteListener;

public interface ByteInputConverter extends AutoCloseable, ByteListener {
    void register(DataInputEventListener listener);
}
