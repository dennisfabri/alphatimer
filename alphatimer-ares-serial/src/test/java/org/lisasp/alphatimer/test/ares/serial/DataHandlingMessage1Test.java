package org.lisasp.alphatimer.test.ares.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.DataInputEventListener;
import org.lisasp.alphatimer.api.ares.serial.events.dropped.UnstructuredInputDroppedEvent;
import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage1;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.KindOfTime;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.MessageType;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.RankInfo;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.TimeType;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.ares.serial.MessageConverter;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.lisasp.alphatimer.test.ares.serial.DataHandlingMessageTestData.*;
import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class DataHandlingMessage1Test {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 6, 21, 14, 53);

    private InputCollector inputCollector;
    private DataInputEventListener listener;

    @BeforeEach
    void prepare() {
        DateTimeFacade dateTimeFacade = mock(DateTimeFacade.class);
        when(dateTimeFacade.now()).thenReturn(TIMESTAMP);

        listener = mock(DataInputEventListener.class);

        MessageConverter messageConverter = new MessageConverter();
        messageConverter.register(listener);

        inputCollector = new InputCollector("TestWK", dateTimeFacade);
        inputCollector.register(messageConverter);
    }

    @AfterEach
    void cleanUp() {
        inputCollector = null;
        listener = null;
    }

    @Test
    void sendMessage1() {
        for (byte b : message1) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
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
                RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1WithRank1() {
        byte[] message1modified = Arrays.copyOf(message1, message1.length);
        message1modified[18] = 0x31;

        for (byte b : message1modified) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
                TIMESTAMP,
                "TestWK",
                new String(message1modified),
                MessageType.OnLineTime,
                KindOfTime.Start,
                TimeType.Empty,
                createUsedLanes(),
                (byte) 2,
                (short) 1,
                (byte) 1,
                (byte) 1,
                RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendMessage1AfterSomeBogusData() {
        for (byte b : bogus) {
            inputCollector.accept(b);
        }
        for (byte b : message1) {
            inputCollector.accept(b);
        }

        verify(listener, times(2)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(TIMESTAMP, "TestWK", bogus));
        verify(listener, times(1)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1)).accept(new DataHandlingMessage1(
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
                RankInfo.Normal));

        verifyNoMoreInteractions(listener);
    }
}
