package org.lisasp.alphatimer.test.api.ares.serial.events;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;
import org.lisasp.alphatimer.api.ares.serial.events.DataInputEvent;
import org.lisasp.alphatimer.api.ares.serial.json.BytesInputEventModule;
import org.lisasp.basics.spring.jms.JsonMessageConverter;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SerializationTests {

    public static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 06, 21, 14, 53, 12, 123456789);
    private Session session;
    private TextMessage message;
    private JsonMessageConverter converter;

    @BeforeEach
    void prepare() throws JMSException {
        message = mock(TextMessage.class);
        doAnswer(answer -> when(message.getText()).thenReturn((String) answer.getArguments()[0]))
                .when(message).setText(any());
        session = mock(Session.class);
        given(session.createTextMessage()).willReturn(message);

        converter = new JsonMessageConverter();
        converter.registerModule(new BytesInputEventModule());
    }

    @AfterEach
    void cleanup() {
        session = null;
        message = null;
        converter = null;
    }

    static DataInputEvent[] provideTestData() {
        return new DataInputEvent[]{
                new BytesInputEvent(TIMESTAMP, "Test", new byte[]{0x01, 0x02}),
                // new Ping(TIMESTAMP, "Test", new byte[]{0x01, 0x02}),
        };
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void objectToStringTest(DataInputEvent event) throws JMSException {
        Message msg = converter.toMessage(event, session);
        DataInputEvent actual = (DataInputEvent) converter.fromMessage(msg);

        assertEquals(event, actual);
    }

}
