package org.lisasp.alphatimer.livetiming.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.heats.service.HeatService;
import org.lisasp.basics.spring.jms.JsonMessageConverter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class Receiver {

    private final JsonMessageConverter converter;
    private final HeatService heatService;

    @JmsListener(destination = "${alphatimer.queue}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(TextMessage message,
                               @Header(JmsHeaders.MESSAGE_ID) String messageId) throws JMSException {
        log.info("received '{}' with MessageId '{}'", message.getText(), messageId);
        heatService.accept((RefinedMessage) converter.fromMessage(message));
    }
}
