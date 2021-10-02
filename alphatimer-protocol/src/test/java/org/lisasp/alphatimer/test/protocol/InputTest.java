package org.lisasp.alphatimer.test.protocol;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.Characters;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class InputTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 6, 21, 14, 53);

    InputCollector inputCollector;
    DataInputEventListener listener;

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
    void acceptInput() {
        inputCollector.accept((byte) 0x01);
        inputCollector.accept((byte) 0x02);
        inputCollector.accept((byte) 0x03);
        inputCollector.accept((byte) 0x05);
        inputCollector.accept((byte) 0x06);

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void longUnstructuredInput() {
        for (int x = 0; x < 10000; x++) {
            inputCollector.accept((byte) 0x02);
        }

        verify(listener, atLeast(1)).accept(Mockito.any());
        verify(listener, atLeast(1)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void close() {
        inputCollector.close();

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void closeWithData() {
        inputCollector.accept((byte) 0x01);
        inputCollector.accept((byte) 0x02);
        inputCollector.accept((byte) 0x03);
        inputCollector.accept((byte) 0x05);
        inputCollector.accept((byte) 0x06);

        inputCollector.close();

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(TIMESTAMP,
                                                                            "TestWK",
                                                                            new byte[]{0x01, 0x02, 0x03, 0x05, 0x06}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithSingleEOT() {
        inputCollector.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(TIMESTAMP,
                                                                            "TestWK",
                                                                            new byte[]{Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithEmptyMessage() {
        inputCollector.accept((byte) 0x01);
        inputCollector.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnstructuredInputDroppedEvent(TIMESTAMP,
                                                                  "TestWK",
                                                                  new byte[]{0x01, Characters.EOT_EndOfText}));
    }

    @Test
    void denyInputWithEmptyMessageAndHighBit() {
        inputCollector.accept((byte) 0x01);
        inputCollector.accept((byte) (Characters.EOT_EndOfText - 128));

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnstructuredInputDroppedEvent(TIMESTAMP,
                                                                  "TestWK",
                                                                  new byte[]{0x01, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithIncompleteMessage() {
        inputCollector.accept((byte) 0x02);
        inputCollector.accept((byte) 0x03);
        inputCollector.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnstructuredInputDroppedEvent(TIMESTAMP,
                                                                  "TestWK",
                                                                  new byte[]{0x02, 0x03, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithInvalidMessage() {
        inputCollector.accept(Characters.SOH_StartOfHeader);
        inputCollector.accept((byte) 0x03);
        inputCollector.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnknownMessageDroppedEvent(TIMESTAMP,
                                                               "TestWK",
                                                               new byte[]{Characters.SOH_StartOfHeader, 0x03, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void emitNoOutputAfterStartSymbolWithoutPreviousInput() {
        inputCollector.accept(Characters.SOH_StartOfHeader);

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void emitOutputAfterStartSymbolWithPreviousInput() {
        inputCollector.accept(Characters.SPACE);
        inputCollector.accept(Characters.SOH_StartOfHeader);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(TIMESTAMP,
                                                                            "TestWK",
                                                                            new byte[]{Characters.SPACE}));

        verifyNoMoreInteractions(listener);
    }
}
