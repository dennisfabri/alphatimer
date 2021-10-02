package org.lisasp.alphatimer.serial;

import org.lisasp.basics.notification.primitive.ByteListener;

public interface SerialPortReader extends AutoCloseable {
    SerialPortReader register(ByteListener listener);

    @Override
    void close();
}
