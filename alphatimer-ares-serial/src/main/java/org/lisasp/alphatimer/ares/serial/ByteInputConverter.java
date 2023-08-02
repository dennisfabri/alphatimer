package org.lisasp.alphatimer.ares.serial;

import org.lisasp.alphatimer.api.ares.serial.BytesInputEventListener;
import org.lisasp.basics.notification.primitive.ByteConsumer;

public interface ByteInputConverter extends AutoCloseable, ByteConsumer {
    void register(BytesInputEventListener listener);
}
