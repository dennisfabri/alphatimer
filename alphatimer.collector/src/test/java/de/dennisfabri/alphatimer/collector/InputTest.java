package de.dennisfabri.alphatimer.collector;

import de.dennisfabri.alphatimer.api.protocol.DataListener;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class InputTest {

    InputCollector alphaTranslator;
    DataListener listener;

    @BeforeEach
    void prepare() {
        listener = mock(DataListener.class);

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
        alphaTranslator.put((byte) 0x01);
        alphaTranslator.put((byte) 0x02);
        alphaTranslator.put((byte) 0x03);
        alphaTranslator.put((byte) 0x05);
        alphaTranslator.put((byte) 0x06);

        verify(listener, times(0)).notify(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void longUnstructuredInput() {
        for (int x = 0; x < 10000; x++) {
            alphaTranslator.put((byte) 0x02);
        }

        verify(listener, atLeast(1)).notify(Mockito.any());
        verify(listener, atLeast(1)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void close() {
        alphaTranslator.close();

        verify(listener, times(0)).notify(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void closeWithData() {
        alphaTranslator.put((byte) 0x01);
        alphaTranslator.put((byte) 0x02);
        alphaTranslator.put((byte) 0x03);
        alphaTranslator.put((byte) 0x05);
        alphaTranslator.put((byte) 0x06);

        alphaTranslator.close();

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(new UnstructuredInputDroppedEvent(new byte[]{0x01, 0x02, 0x03, 0x05, 0x06}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithSingleEOT() {
        alphaTranslator.put(Characters.EOT_EndOfText);

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(new UnstructuredInputDroppedEvent(new byte[]{Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithEmptyMessage() {
        alphaTranslator.put((byte) 0x01);
        alphaTranslator.put(Characters.EOT_EndOfText);

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener,
               times(1)).notify(new UnstructuredInputDroppedEvent(new byte[]{0x01, Characters.EOT_EndOfText}));
    }

    @Test
    void denyInputWithEmptyMessageAndHighBit() {
        alphaTranslator.put((byte) 0x01);
        alphaTranslator.put((byte) (Characters.EOT_EndOfText - 128));

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener,
               times(1)).notify(new UnstructuredInputDroppedEvent(new byte[]{0x01, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithIncompleteMessage() {
        alphaTranslator.put((byte) 0x02);
        alphaTranslator.put((byte) 0x03);
        alphaTranslator.put(Characters.EOT_EndOfText);

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener,
               times(1)).notify(new UnstructuredInputDroppedEvent(new byte[]{0x02, 0x03, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void denyInputWithInvalidMessage() {
        alphaTranslator.put(Characters.SOH_StartOfHeader);
        alphaTranslator.put((byte) 0x03);
        alphaTranslator.put(Characters.EOT_EndOfText);

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener,
               times(1)).notify(new UnknownMessageDroppedEvent(new byte[]{Characters.SOH_StartOfHeader, 0x03, Characters.EOT_EndOfText}));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void emitNoOutputAfterStartSymbolWithoutPreviousInput() {
        alphaTranslator.put(Characters.SOH_StartOfHeader);

        verify(listener, times(0)).notify(Mockito.any());

        verifyNoMoreInteractions(listener);
    }

    @Test
    void emitOutputAfterStartSymbolWithPreviousInput() {
        alphaTranslator.put(Characters.SPACE);
        alphaTranslator.put(Characters.SOH_StartOfHeader);

        verify(listener, times(1)).notify(Mockito.any());
        verify(listener, times(1)).notify(new UnstructuredInputDroppedEvent(new byte[]{Characters.SPACE}));

        verifyNoMoreInteractions(listener);
    }
}
