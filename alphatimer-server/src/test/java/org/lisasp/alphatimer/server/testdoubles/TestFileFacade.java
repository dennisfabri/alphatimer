package org.lisasp.alphatimer.server.testdoubles;

import org.lisasp.alphatimer.jre.io.FileFacade;

public class TestFileFacade implements FileFacade {
    @Override
    public void write(String filename, byte b) {
    }

    @Override
    public byte[] read(String filename) {
        return new byte[0];
    }

    @Override
    public String getSeparator() {
        return "/";
    }
}
