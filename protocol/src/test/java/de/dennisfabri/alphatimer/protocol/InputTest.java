package de.dennisfabri.alphatimer.protocol;

import de.dennisfabri.alphatimer.api.protocol.DataInputEventListener;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class InputTest {

    InputCollector alphaTranslator;
    DataInputEventListener listener;

    @BeforeEach
    void prepare() {
        listener = mock(DataInputEventListener.class);

        alphaTranslator = new InputCollector();
        alphaTranslator.register(listener);
    }

    @AfterEach
    void cleanUp() {
        alphaTranslator = null;
        listener = null;
    }

    @Test
    void acceptInput() {
        alphaTranslator.accept((byte) 0x01);
        alphaTranslator.accept((byte) 0x02);
        alphaTranslator.accept((byte) 0x03);
        alphaTranslator.accept((byte) 0x05);
        alphaTranslator.accept((byte) 0x06);

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void longUnstructuredInput() {
        for (int x = 0; x < 10000; x++) {
            alphaTranslator.accept((byte) 0x02);
        }

        verify(listener, atLeast(1)).accept(Mockito.any());
        verify(listener, atLeast(1)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void close() {
        alphaTranslator.close();

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void closeWithData() {
        alphaTranslator.accept((byte) 0x01);
        alphaTranslator.accept((byte) 0x02);
        alphaTranslator.accept((byte) 0x03);
        alphaTranslator.accept((byte) 0x05);
        alphaTranslator.accept((byte) 0x06);

        alphaTranslator.close();

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(new byte[]{0x01, 0x02, 0x03, 0x05, 0x06}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithSingleEOT() {
        alphaTranslator.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(new byte[]{Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithEmptyMessage() {
        alphaTranslator.accept((byte) 0x01);
        alphaTranslator.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnstructuredInputDroppedEvent(new byte[]{0x01, Characters.EOT_EndOfText}));
    }

    @Test
    void denyInputWithEmptyMessageAndHighBit() {
        alphaTranslator.accept((byte) 0x01);
        alphaTranslator.accept((byte) (Characters.EOT_EndOfText - 128));

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnstructuredInputDroppedEvent(new byte[]{0x01, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithIncompleteMessage() {
        alphaTranslator.accept((byte) 0x02);
        alphaTranslator.accept((byte) 0x03);
        alphaTranslator.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnstructuredInputDroppedEvent(new byte[]{0x02, 0x03, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithInvalidMessage() {
        alphaTranslator.accept(Characters.SOH_StartOfHeader);
        alphaTranslator.accept((byte) 0x03);
        alphaTranslator.accept(Characters.EOT_EndOfText);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener,
               times(1)).accept(new UnknownMessageDroppedEvent(new byte[]{Characters.SOH_StartOfHeader, 0x03, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void emitNoOutputAfterStartSymbolWithoutPreviousInput() {
        alphaTranslator.accept(Characters.SOH_StartOfHeader);

        verify(listener, times(0)).accept(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void emitOutputAfterStartSymbolWithPreviousInput() {
        alphaTranslator.accept(Characters.SPACE);
        alphaTranslator.accept(Characters.SOH_StartOfHeader);

        verify(listener, times(1)).accept(Mockito.any());
        verify(listener, times(1)).accept(new UnstructuredInputDroppedEvent(new byte[]{Characters.SPACE}));

        verifyNoMoreInteractions(listener);
    }
}
