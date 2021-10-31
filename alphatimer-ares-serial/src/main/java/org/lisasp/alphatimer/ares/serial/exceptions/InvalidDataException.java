package org.lisasp.alphatimer.ares.serial.exceptions;

public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Exception cause) {
        super(message, cause);
    }
}
