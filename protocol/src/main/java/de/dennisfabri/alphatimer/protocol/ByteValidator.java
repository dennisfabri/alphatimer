package de.dennisfabri.alphatimer.protocol;

class ByteValidator {

    byte ensureValidity(byte entry) {
        // The protocol is based on 7-bit ascii. The eighths bit is not used.
        return (byte) (entry & 127);
    }
}
