package org.lisasp.alphatimer.ares.serial;

import org.lisasp.alphatimer.api.ares.serial.BytesInputEventListener;
import org.lisasp.basics.notification.primitive.ByteListener;

public interface ByteInputConverter extends AutoCloseable, ByteListener {
    void register(BytesInputEventListener listener);
}
