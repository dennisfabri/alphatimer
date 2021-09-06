package org.lisasp.alphatimer.test.spring.jms;

import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.spring.jms.JsonMessageConverter;
import org.lisasp.alphatimer.test.spring.jms.data.TestData1;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class JsonMessageConverterTest {

    private final String content = "org.lisasp.alphatimer.test.spring.jms.data.TestData1|{\"text\":\"abc\",\"value\":123}";
    private final TestData1 testData1 = new TestData1("abc", 123);

    @Test
    public void objectToStringTest() throws JMSException {
        Session session = mock(Session.class);
        TextMessage message = mock(TextMessage.class);
        given(session.createTextMessage()).willReturn(message);

        JsonMessageConverter converter = new JsonMessageConverter();
        Message msg = converter.toMessage(testData1, session);

        verify(message, times(1)).setText(content);
    }

    @Test
    public void stringToObjectTest() throws JMSException {
        Session session = mock(Session.class);
        TextMessage message = mock(TextMessage.class);
        given(session.createTextMessage()).willReturn(message);
        given(message.getText()).willReturn(content);

        JsonMessageConverter converter = new JsonMessageConverter();
        Object actual = converter.fromMessage(message);
        
        assertEquals(testData1, actual);
    }
}
