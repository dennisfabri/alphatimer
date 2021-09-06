package org.lisasp.alphatimer.test.datatests;

import org.junit.jupiter.api.*;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.*;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import org.lisasp.alphatimer.api.protocol.events.messages.Message;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.alphatimer.datatests.TestData;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.lisasp.alphatimer.test.datatests.testdoubles.DateTimeFacadeTestDouble;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class CollectorDataTest {

    private static final boolean verbose = false;
    private static TestData testData;

    private InputCollector inputCollector;
    MessageConverter messageConverter;
    private DataInputEventListener listener;

    @BeforeAll
    static void prepareData() throws IOException {
        testData = new TestData();
        testData.prepare();
    }

    @AfterAll
    static void cleanup() throws IOException {
        testData.cleanup();
    }

    @BeforeEach
    void prepare() {
        DateTimeFacade dateTimeFacade = new DateTimeFacadeTestDouble(LocalDateTime.of(2021, 6, 21, 14, 53));

        listener = mock(DataInputEventListener.class);

        messageConverter = new MessageConverter();
        messageConverter.register(listener);
        if (verbose) {
            inputCollector.register(event -> {
                if (event instanceof InputDataDroppedEvent) {
                    System.out.println(event);
                }
            });
        }

        inputCollector = new InputCollector("TestWK", dateTimeFacade);
        inputCollector.register(messageConverter);
    }

    @AfterEach
    void cleanUp() {
        inputCollector = null;
        listener = null;
    }

    @Test
    void DM2008Freitag() throws IOException {
        byte[] data = testData.readSerialInput("DM2008Freitag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(2812)).accept(Mockito.any());

        verify(listener, times(2812)).accept(Mockito.any(Message.class));
        verify(listener, times(0)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).accept(Mockito.any(Ping.class));
        verify(listener, times(1406)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1406)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DM2008Samstag() throws IOException {
        byte[] data = testData.readSerialInput("DM2008Samstag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(2952)).accept(Mockito.any());

        verify(listener, times(2952)).accept(Mockito.any(Message.class));
        verify(listener, times(0)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).accept(Mockito.any(Ping.class));
        verify(listener, times(1476)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1476)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DM2009() throws IOException {
        byte[] data = testData.readSerialInput("DM2009");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(16610)).accept(Mockito.any());

        verify(listener, times(16605)).accept(Mockito.any(Message.class));
        verify(listener, times(5)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).accept(Mockito.any(Ping.class));
        verify(listener, times(8303)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(8302)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(5)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DM2010() throws IOException {
        byte[] data = testData.readSerialInput("DM2010");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(4874)).accept(Mockito.any());

        verify(listener, times(4874)).accept(Mockito.any(Message.class));
        verify(listener, times(0)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(0)).accept(Mockito.any(Ping.class));
        verify(listener, times(2437)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(2437)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void JRP2019Freitag() throws IOException {
        byte[] data = testData.readSerialInput("JRP2019Freitag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(68)).accept(Mockito.any());

        verify(listener, times(67)).accept(Mockito.any(Message.class));
        verify(listener, times(1)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(61)).accept(Mockito.any(Ping.class));
        verify(listener, times(3)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(3)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(1)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void JRP2019Samstag() throws IOException {
        byte[] data = testData.readSerialInput("JRP2019Samstag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(33994)).accept(Mockito.any());

        verify(listener, times(33068)).accept(Mockito.any(Message.class));
        verify(listener, times(926)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(29162)).accept(Mockito.any(Ping.class));
        verify(listener, times(1953)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1953)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(926)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void JRP2019Sonntag() throws IOException {
        byte[] data = testData.readSerialInput("JRP2019Sonntag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        // verify(listener, times(31101)).accept(Mockito.any());

        verify(listener, times(22808)).accept(Mockito.any(Message.class));
        verify(listener, times(8293)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(20510)).accept(Mockito.any(Ping.class));
        verify(listener, times(1149)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1149)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(8293)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DMM2019Freitag() throws IOException {
        byte[] data = testData.readSerialInput("DMM2019Freitag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(2504)).accept(Mockito.any());

        verify(listener, times(2503)).accept(Mockito.any(Message.class));
        verify(listener, times(1)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(2475)).accept(Mockito.any(Ping.class));
        verify(listener, times(14)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(14)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(1)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DMM2019Samstag() throws IOException {
        byte[] data = testData.readSerialInput("DMM2019Samstag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(43440)).accept(Mockito.any());

        verify(listener, times(43438)).accept(Mockito.any(Message.class));
        verify(listener, times(2)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(39566)).accept(Mockito.any(Ping.class));
        verify(listener, times(1936)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1936)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(2)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DMM2019Sonntag() throws IOException {
        byte[] data = testData.readSerialInput("DMM2019Sonntag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(34796)).accept(Mockito.any());

        verify(listener, times(34790)).accept(Mockito.any(Message.class));
        verify(listener, times(6)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(31418)).accept(Mockito.any(Ping.class));
        verify(listener, times(1686)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1686)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(6)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DP2019Freitag() throws IOException {
        byte[] data = testData.readSerialInput("DP2019Freitag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(32740)).accept(Mockito.any());

        verify(listener, times(32739)).accept(Mockito.any(Message.class));
        verify(listener, times(1)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(29731)).accept(Mockito.any(Ping.class));
        verify(listener, times(1504)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1504)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(1)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }

    @Test
    void DP2019Samstag() throws IOException {
        byte[] data = testData.readSerialInput("DP2019Samstag");

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        verify(listener, times(28659)).accept(Mockito.any());

        verify(listener, times(28657)).accept(Mockito.any(Message.class));
        verify(listener, times(2)).accept(Mockito.any(InputDataDroppedEvent.class));

        verify(listener, times(25785)).accept(Mockito.any(Ping.class));
        verify(listener, times(1436)).accept(Mockito.any(DataHandlingMessage1.class));
        verify(listener, times(1436)).accept(Mockito.any(DataHandlingMessage2.class));

        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage1DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(DataHandlingMessage2DroppedEvent.class));
        verify(listener, times(0)).accept(Mockito.any(UnknownMessageDroppedEvent.class));
        verify(listener, times(2)).accept(Mockito.any(UnstructuredInputDroppedEvent.class));

        verifyNoMoreInteractions(listener);
    }
}
