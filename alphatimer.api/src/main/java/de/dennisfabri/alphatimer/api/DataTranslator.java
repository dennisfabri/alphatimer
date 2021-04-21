package de.dennisfabri.alphatimer.api;

public interface DataTranslator extends AutoCloseable {
    void put(byte entry);

    void register(DataListener listener);
}
