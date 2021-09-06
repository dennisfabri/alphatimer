package org.lisasp.alphatimer.server.mq;

import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.server.ConfigurationValues;
import org.lisasp.alphatimer.spring.jms.JsonMessageConverter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {

  private final String queue;

  private final JmsTemplate jmsTemplate;

  public Sender(ConfigurationValues values, JmsTemplate template) {
    this.queue = values.getRefinedQueueName();
    this.jmsTemplate = template;
  }

  public void send(RefinedMessage message) {
    try {
      log.info("Sending: " + message.toString());
      jmsTemplate.convertAndSend(queue, message);
    } catch (Exception ex) {
      log.error("Send", ex);
    }
  }
}
