package org.lisasp.alphatimer.test.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.*;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static org.lisasp.alphatimer.test.protocol.DataHandlingMessageTestData.*;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class MessageConverterTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 6, 1, 10, 0);

    public static final DataHandlingMessage1 TestDataHandlingMessage1 = new DataHandlingMessage1(
            TIMESTAMP,
            "TestWK",
            new String(message1),
            MessageType.OnLineTime,
            KindOfTime.Start,
            TimeType.Empty,
            createUsedLanes(),
            (byte) 2,
            (short) 1,
            (byte) 1,
            (byte) 0,
            RankInfo.Normal);
    public static final DataHandlingMessage2 TestDataHandlingMessage2 = new DataHandlingMessage2(
            TIMESTAMP,
            "TestWK",
            new String(message2),
            (byte) 1,
            (byte) 0,
            112853930,
            TimeInfo.Normal,
            TimeMarker.Empty);

    private MessageConverter messageConverter;
    private Consumer<DataInputEvent> listener;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void prepare() {
        DateTimeFacade datetime = Mockito.mock(DateTimeFacade.class);
        Mockito.when(datetime.now()).thenReturn(LocalDateTime.of(2021, 6, 1, 10, 0));

        listener = Mockito.mock(Consumer.class);
        messageConverter = new MessageConverter();
        messageConverter.register(listener);
    }

    @AfterEach
    void cleanUp() {
        messageConverter = null;
        listener = null;
    }

    @Test
    void sendMessage1And2() {
        messageConverter.accept(new BytesInputEvent(TIMESTAMP, "TestWK", message1));
        messageConverter.accept(new BytesInputEvent(TIMESTAMP, "TestWK", message2));

        verify(listener, times(1)).accept(TestDataHandlingMessage1);
        verify(listener, times(1)).accept(TestDataHandlingMessage2);

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AndPing() {
        messageConverter.accept(new BytesInputEvent(TIMESTAMP, "TestWK", message1));

        verify(listener, times(1)).accept(TestDataHandlingMessage1);

        verifyNoMoreInteractions(listener);
    }
}
