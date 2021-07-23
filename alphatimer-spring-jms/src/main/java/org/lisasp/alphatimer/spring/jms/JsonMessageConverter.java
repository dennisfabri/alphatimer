package org.lisasp.alphatimer.spring.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
@Slf4j
public class JsonMessageConverter implements MessageConverter {

    private final ObjectMapper mapper = new ObjectMapper();

    private final static String messageFormat = "%s|%s";

    public JsonMessageConverter() {
        log.info("Initializing");
        mapper.findAndRegisterModules();
    }

    @Override
    public Message toMessage(Object value, Session session) throws JMSException, MessageConversionException {
        try {
            TextMessage message = session.createTextMessage();
            message.setText(valueToPayload(value));
            return message;
        } catch (JsonProcessingException e) {
            throw new MessageConversionException("Could not convert to json.", e);
        }
    }

    private String valueToPayload(Object value) throws JsonProcessingException {
        return String.format(messageFormat, getClassName(value), mapper.writeValueAsString(value));
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        try {
            TextMessage textMessage = (TextMessage) message;
            String payload = textMessage.getText();

            return mapper.readValue(extractJsonFromPayload(payload), extractClassFromPayload(payload));
        } catch (Exception e) {
            throw new MessageConversionException("Could not convert from json.", e);
        }
    }

    @NotNull
    private Class<?> extractClassFromPayload(String payload) throws ClassNotFoundException {
        return getClass(payload.substring(0, payload.indexOf('|')));
    }

    @NotNull
    private String extractJsonFromPayload(String payload) {
        return payload.substring(payload.indexOf('|') + 1);
    }

    @NotNull
    private Class<?> getClass(String classname) throws ClassNotFoundException {
        return Class.forName(classname);
    }

    private String getClassName(Object value) {
        return value.getClass().getName();
    }
}
