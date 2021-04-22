package de.dennisfabri.alphatimer.datatests;

import de.dennisfabri.alphatimer.api.protocol.DataListener;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.*;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Message;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Ping;
import de.dennisfabri.alphatimer.collector.InputCollector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CollectorDataTest {

    private static final boolean verbose = false;

    private InputCollector alphaTranslator;
    private DataListener listener;

    private static final Testdata testdata = new Testdata();

    @BeforeAll
    static void prepareData() throws IOException {
        testdata.prepare();
    }

    @BeforeEach
    void prepare() {
        listener = mock(DataListener.class);

        alphaTranslator = new InputCollector();
        alphaTranslator.register(listener);
        if (verbose) {
            alphaTranslator.register(event -> {
                if (event instanceof InputDataDroppedEvent) {
                    System.out.println(event);
                }
            });
        }
    }

    @AfterEach
    void cleanUp() {
        alphaTranslator = null;
        listener = null;
    }

    @Test
    void DM2008Freitag() throws IOException {
        byte[] data = testdata.readSerialInput("DM2008Freitag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(2812)).notify(Mockito.any());

        verify(listener, times(2812)).notify(Mockito.any(Message.class));
        verify(listener, times(0)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).notify(Mockito.any(Ping.class));
        verify(listener, times(1406)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1406)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DM2008Samstag() throws IOException {
        byte[] data = testdata.readSerialInput("DM2008Samstag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(2952)).notify(Mockito.any());

        verify(listener, times(2952)).notify(Mockito.any(Message.class));
        verify(listener, times(0)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).notify(Mockito.any(Ping.class));
        verify(listener, times(1476)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1476)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DM2009() throws IOException {
        byte[] data = testdata.readSerialInput("DM2009");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(16610)).notify(Mockito.any());

        verify(listener, times(16605)).notify(Mockito.any(Message.class));
        verify(listener, times(5)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).notify(Mockito.any(Ping.class));
        verify(listener, times(8303)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(8302)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(5)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DM2010() throws IOException {
        byte[] data = testdata.readSerialInput("DM2010");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(4874)).notify(Mockito.any());

        verify(listener, times(4874)).notify(Mockito.any(Message.class));
        verify(listener, times(0)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).notify(Mockito.any(Ping.class));
        verify(listener, times(2437)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(2437)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void JRP2019Freitag() throws IOException {
        byte[] data = testdata.readSerialInput("JRP2019Freitag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(68)).notify(Mockito.any());

        verify(listener, times(67)).notify(Mockito.any(Message.class));
        verify(listener, times(1)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(61)).notify(Mockito.any(Ping.class));
        verify(listener, times(3)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(3)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(1)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void JRP2019Samstag() throws IOException {
        byte[] data = testdata.readSerialInput("JRP2019Samstag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(33994)).notify(Mockito.any());

        verify(listener, times(33068)).notify(Mockito.any(Message.class));
        verify(listener, times(926)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(29162)).notify(Mockito.any(Ping.class));
        verify(listener, times(1953)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1953)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(926)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void JRP2019Sonntag() throws IOException {
        byte[] data = testdata.readSerialInput("JRP2019Sonntag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(31108)).notify(Mockito.any());

        verify(listener, times(22808)).notify(Mockito.any(Message.class));
        verify(listener, times(8300)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(20510)).notify(Mockito.any(Ping.class));
        verify(listener, times(1149)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1149)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(8300)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DMM2019Freitag() throws IOException {
        byte[] data = testdata.readSerialInput("DMM2019Freitag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(2504)).notify(Mockito.any());

        verify(listener, times(2503)).notify(Mockito.any(Message.class));
        verify(listener, times(1)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(2475)).notify(Mockito.any(Ping.class));
        verify(listener, times(14)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(14)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(1)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DMM2019Samstag() throws IOException {
        byte[] data = testdata.readSerialInput("DMM2019Samstag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(43440)).notify(Mockito.any());

        verify(listener, times(43438)).notify(Mockito.any(Message.class));
        verify(listener, times(2)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(39566)).notify(Mockito.any(Ping.class));
        verify(listener, times(1936)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1936)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(2)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DMM2019Sonntag() throws IOException {
        byte[] data = testdata.readSerialInput("DMM2019Sonntag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(34796)).notify(Mockito.any());

        verify(listener, times(34790)).notify(Mockito.any(Message.class));
        verify(listener, times(6)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(31418)).notify(Mockito.any(Ping.class));
        verify(listener, times(1686)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1686)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(6)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DP2019Freitag() throws IOException {
        byte[] data = testdata.readSerialInput("DP2019Freitag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(32740)).notify(Mockito.any());

        verify(listener, times(32739)).notify(Mockito.any(Message.class));
        verify(listener, times(1)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(29731)).notify(Mockito.any(Ping.class));
        verify(listener, times(1504)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1504)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(1)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DP2019Samstag() throws IOException {
        byte[] data = testdata.readSerialInput("DP2019Samstag");

        for (byte d : data) {
            alphaTranslator.put(d);
        }
        alphaTranslator.close();

        verify(listener, times(28659)).notify(Mockito.any());

        verify(listener, times(28657)).notify(Mockito.any(Message.class));
        verify(listener, times(2)).notify(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(25785)).notify(Mockito.any(Ping.class));
        verify(listener, times(1436)).notify(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1436)).notify(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).notify(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(2)).notify(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }
}
