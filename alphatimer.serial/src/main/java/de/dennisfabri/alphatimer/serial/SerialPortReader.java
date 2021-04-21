package de.dennisfabri.alphatimer.serial;

public interface SerialPortReader extends AutoCloseable {
    @Override
    void close();
}
