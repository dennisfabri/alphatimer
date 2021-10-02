package org.lisasp.alphatimer.server.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.server.SerialInterpreter;
import org.lisasp.basics.spring.jms.JsonMessageConverter;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class Receiver {

    private final JsonMessageConverter converter;
    private final SerialInterpreter serialInterpreter;

    @JmsListener(destination = "${alphatimer.queues.ares}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(TextMessage message,
                               @Header(JmsHeaders.MESSAGE_ID) String messageId) throws JMSException {
        log.info("received '{}' with MessageId '{}'", message.getText(), messageId);
        serialInterpreter.accept((BytesInputEvent) converter.fromMessage(message));
    }
}
