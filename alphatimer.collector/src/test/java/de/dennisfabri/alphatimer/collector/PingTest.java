package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.DataListener;
import de.dennisfabri.alphatimer.api.events.dropped.UnknownMessageDroppedEvent;
import de.dennisfabri.alphatimer.api.events.messages.Ping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Corresponds to chapter 2
 */
class PingTest {

    private AlphaTranslator alphaTranslator;
    private DataListener listener;

    @BeforeEach
    void prepare() {
        alphaTranslator = new AlphaTranslator();
        listener = mock(DataListener.class);
        alphaTranslator.register(listener);
    }

    @AfterEach
    void cleanUp() {
        alphaTranslator = null;
        listener = null;
    }

    @Test
    void sendPing() {
        for (byte b : DataHandlingMessageTestData.ping) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(Ping.class));
        verify(listener, times(1)).notify(new Ping(new byte[]{0x54, 0x50}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendPingInvalid1() {
        byte[] modifiedPing = Arrays.copyOf(DataHandlingMessageTestData.ping, DataHandlingMessageTestData.ping.length);
        modifiedPing[1] = 0x00;

        for (byte b : modifiedPing) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener,
               times(1)).notify(new UnknownMessageDroppedEvent(new byte[]{Characters.SOH_StartOfHeader, 0x00, 0x39, Characters.DC4_Command, 0x54, 0x50, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendPingInvalid2() {
        byte[] modifiedPing = Arrays.copyOf(DataHandlingMessageTestData.ping, DataHandlingMessageTestData.ping.length);
        modifiedPing[2] = 0x00;

        for (byte b : modifiedPing) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener,
               times(1)).notify(new UnknownMessageDroppedEvent(new byte[]{Characters.SOH_StartOfHeader, Characters.DC2_Periphery, 0x00, Characters.DC4_Command, 0x54, 0x50, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void sendPingInvalid3() {
        byte[] modifiedPing = Arrays.copyOf(DataHandlingMessageTestData.ping, DataHandlingMessageTestData.ping.length);
        modifiedPing[3] = 0x00;

        for (byte b : modifiedPing) {
            alphaTranslator.put(b);
        }

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener,
               times(1)).notify(new UnknownMessageDroppedEvent(new byte[]{Characters.SOH_StartOfHeader, Characters.DC2_Periphery, 0x39, 0x00, 0x54, 0x50, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }
}
