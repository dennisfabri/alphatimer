package org.lisasp.alphatimer.serial;

public interface SerialPortReader extends AutoCloseable {
    @Override
    void close();
}
