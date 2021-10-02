package org.lisasp.alphatimer.protocol;

import org.lisasp.alphatimer.api.protocol.BytesInputEventListener;
import org.lisasp.basics.notification.primitive.ByteListener;

public interface ByteInputConverter extends AutoCloseable, ByteListener {
    void register(BytesInputEventListener listener);
}
