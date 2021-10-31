package org.lisasp.alphatimer.serialportlistener.mq;

import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;
import org.lisasp.alphatimer.serialportlistener.ConfigurationValues;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {

    private final JmsTemplate jmsTemplate;

    public Sender(ConfigurationValues values, JmsTemplate template) {
        this.jmsTemplate = template;
    }

    public void send(BytesInputEvent message) {
        try {
            log.info("Sending: " + message.toString());
            jmsTemplate.convertAndSend(message);
        } catch (Exception ex) {
            log.error("Send", ex);
        }
    }
}
