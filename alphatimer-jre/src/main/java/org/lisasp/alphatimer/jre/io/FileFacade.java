package org.lisasp.alphatimer.jre.io;

import java.io.IOException;

public interface FileFacade {
    void write(String filename, byte b) throws IOException;

    byte[] read(String filename) throws IOException;

    String getSeparator();
}
