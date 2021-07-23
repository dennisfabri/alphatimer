package org.lisasp.alphatimer.messaging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionCatchingNotifier<T> extends Notifier<T> {

    public void accept(T event) {
        dataListeners.forEach(
                l -> {
                    try {
                        l.accept(event);
                    } catch (RuntimeException re) {
                        log.warn("Problem notifying listener.", re);
                    }
                }
        );
    }
}
