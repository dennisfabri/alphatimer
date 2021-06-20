package org.lisasp.alphatimer.messaging;

import lombok.Synchronized;

import java.util.ArrayList;
import java.util.List;

public class ByteNotifier {

    private final List<ByteListener> dataListeners = new ArrayList<>();

    @Synchronized("dataListeners")
    public void accept(byte event) {
        dataListeners.forEach(dl -> dl.accept(event));
    }

    @Synchronized("dataListeners")
    public void register(ByteListener dataListener) {
        dataListeners.add(dataListener);
    }
}
