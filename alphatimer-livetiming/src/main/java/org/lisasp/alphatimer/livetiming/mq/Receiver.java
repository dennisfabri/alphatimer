package org.lisasp.alphatimer.livetiming.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Receiver {

    private final CurrentHeatService currentHeatService;

    @JmsListener(destination = "${alphatimer.queue}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(RefinedMessage message,
                               @Header(JmsHeaders.MESSAGE_ID) String messageId) {
        log.info("received '{}' with MessageId '{}'", message.toString(), messageId);
        currentHeatService.accept(message);
    }
}
