package org.lisasp.alphatimer.api.serial.exceptions;

import java.io.IOException;

public class PortAccessException extends IOException {
    public PortAccessException(String port, Throwable e) {
        super(String.format("Problem accessing port '%s'", port == null ? "<null>" : port), e);
    }
}
