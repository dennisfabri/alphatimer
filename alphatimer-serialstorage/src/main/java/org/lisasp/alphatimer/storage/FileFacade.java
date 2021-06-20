package org.lisasp.alphatimer.storage;

import java.io.IOException;

public interface FileFacade {
    void write(String filename, byte b) throws IOException;

    byte[] read(String filename) throws IOException;

    String getSeparator();
}
