package org.lisasp.alphatimer.server.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.server.SerialInterpreter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Receiver {

    private final SerialInterpreter serialInterpreter;

    @JmsListener(destination = "${alphatimer.queues.ares}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(DataInputEvent message,
                               @Header(JmsHeaders.MESSAGE_ID) String messageId) {
        log.info("received '{}' with MessageId '{}'", message.toString(), messageId);
        serialInterpreter.accept(message);
    }
}
