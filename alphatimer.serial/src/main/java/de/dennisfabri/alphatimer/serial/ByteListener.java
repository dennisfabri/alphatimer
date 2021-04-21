package de.dennisfabri.alphatimer.serial;

import java.io.IOException;

public interface ByteListener {
    void notify(byte data) throws IOException;
}
