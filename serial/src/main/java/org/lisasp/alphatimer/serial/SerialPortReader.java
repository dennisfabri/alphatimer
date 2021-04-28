package org.lisasp.alphatimer.serial;

import org.lisasp.alphatimer.messaging.ByteListener;

public interface SerialPortReader extends AutoCloseable {
    SerialPortReader register(ByteListener listener);

    @Override
    void close();
}
