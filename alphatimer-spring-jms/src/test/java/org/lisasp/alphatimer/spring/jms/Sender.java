package org.lisasp.alphatimer.spring.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Sender {
    private final JmsTemplate jmsTemplate;

    public void send(String destination, Object person) {
        log.info("sending person='{}' to destination='{}'", person,
                    destination);
        jmsTemplate.convertAndSend(destination, person);
    }
}
