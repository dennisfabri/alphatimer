package org.lisasp.alphatimer.api.serial;

import org.lisasp.basics.notification.primitive.ByteConsumer;

public interface SerialPortReader extends AutoCloseable {
    SerialPortReader register(ByteConsumer listener);

    @Override
    void close();
}
