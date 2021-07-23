package org.lisasp.alphatimer.spring.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@RequiredArgsConstructor
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${queue.boot}")
    public void receive(Object person) {
        log.info("received person='{}'", person);
        latch.countDown();
    }
}
