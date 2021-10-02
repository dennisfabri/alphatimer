package org.lisasp.alphatimer.test.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.Characters;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class PingTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 6, 21, 15, 51);

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
    void sendPing() {
        for (byte b : DataHandlingMessageTestData.ping) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(Ping.class));
        verify(listener, times(1)).accept(new Ping(TIMESTAMP, "TestWK", new byte[]{0x54, 0x50}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendPingInvalid1() {
        byte[] modifiedPing = Arrays.copyOf(DataHandlingMessageTestData.ping, DataHandlingMessageTestData.ping.length);
        modifiedPing[1] = 0x00;

        for (byte b : modifiedPing) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener,
               times(1)).accept(new UnknownMessageDroppedEvent(TIMESTAMP,
                                                               "TestWK",
                                                               new byte[]{Characters.SOH_StartOfHeader, 0x00, 0x39, Characters.DC4_Command, 0x54, 0x50, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendPingInvalid2() {
        byte[] modifiedPing = Arrays.copyOf(DataHandlingMessageTestData.ping, DataHandlingMessageTestData.ping.length);
        modifiedPing[2] = 0x00;

        for (byte b : modifiedPing) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener,
               times(1)).accept(new UnknownMessageDroppedEvent(TIMESTAMP,
                                                               "TestWK",
                                                               new byte[]{Characters.SOH_StartOfHeader, Characters.DC2_Periphery, 0x00, Characters.DC4_Command, 0x54, 0x50, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendPingInvalid3() {
        byte[] modifiedPing = Arrays.copyOf(DataHandlingMessageTestData.ping, DataHandlingMessageTestData.ping.length);
        modifiedPing[3] = 0x00;

        for (byte b : modifiedPing) {
            inputCollector.accept(b);
        }

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener,
               times(1)).accept(new UnknownMessageDroppedEvent(TIMESTAMP,
                                                               "TestWK",
                                                               new byte[]{Characters.SOH_StartOfHeader, Characters.DC2_Periphery, 0x39, 0x00, 0x54, 0x50, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }
}
