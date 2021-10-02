package org.lisasp.alphatimer.test.datatests;

import lombok.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.lisasp.alphatimer.api.protocol.DataInputEventListener;
import org.lisasp.alphatimer.api.protocol.events.dropped.*;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.alphatimer.datatests.TestData;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.lisasp.alphatimer.test.datatests.testdoubles.DateTimeFacadeTestDouble;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputCollectorDataTest {

    private static TestData testData;

    private InputCollector inputCollector;
    private HitCounter messageHitCount;

    @Value
    static class TestDescription {
        private final String name;
        private final int pings;
        private final int dataHandlingMessage1s;
        private final int dataHandlingMessage2s;
        private final int dataHandling1DroppedEvents;
        private final int dataHandling2DroppedEvents;
        private final int unknownDroppedEvents;
        private final int unstructuredInputDroppedEvents;

        @Override
        public String toString() {
            return name;
        }
    }

    static TestDescription[] provideTestDescriptions() {
        return new TestDescription[]{
                new TestDescription("DM2008-Freitag", 0, 1406, 1406, 0, 0, 0, 0),
                new TestDescription("DM2008-Samstag", 0, 1476, 1476, 0, 0, 0, 0),
                new TestDescription("DM2009", 0, 8303, 8302, 0, 0, 0, 5),
                new TestDescription("DM2010", 0, 2437, 2437, 0, 0, 0, 0),
                new TestDescription("DMM2019-Freitag", 2475, 14, 14, 0, 0, 0, 1),
                new TestDescription("DMM2019-Samstag", 39566, 1936, 1936, 0, 0, 0, 2),
                new TestDescription("DMM2019-Sonntag", 31418, 1686, 1686, 0, 0, 0, 6),
                new TestDescription("DP2019-Freitag", 29731, 1504, 1504, 0, 0, 0, 1),
                new TestDescription("DP2019-Samstag", 25785, 1436, 1436, 0, 0, 0, 2),
                new TestDescription("DEM2021-Samstag", 20734, 608, 608, 0, 0, 0, 1),
                new TestDescription("DEM2021-Sonntag", 28242, 1104, 1104, 0, 0, 0, 1),
        };
    }

    @BeforeEach
    void prepare() {
        testData = new TestData();

        DateTimeFacade dateTimeFacade = new DateTimeFacadeTestDouble(LocalDateTime.of(2021, 6, 21, 14, 53));
        messageHitCount = new HitCounter();

        DataInputEventListener listener = dataInputEvent -> messageHitCount.increaseHitCount(dataInputEvent.getClass());

        MessageConverter messageConverter = new MessageConverter();
        messageConverter.register(listener);

        inputCollector = new InputCollector("TestWK", dateTimeFacade);
        inputCollector.register(messageConverter);
    }

    @AfterEach
    void cleanUp() {
        inputCollector = null;
    }

    @ParameterizedTest
    @MethodSource("provideTestDescriptions")
    void inputCollectorTest(TestDescription description) throws IOException {
        byte[] data = testData.readSerialInput(description.getName());

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        assertEquals(description.getPings(), messageHitCount.getHitCount(Ping.class), "Ping");
        assertEquals(description.getDataHandlingMessage1s(), messageHitCount.getHitCount(DataHandlingMessage1.class), "DataHandlingMessage1");
        assertEquals(description.getDataHandlingMessage2s(), messageHitCount.getHitCount(DataHandlingMessage2.class), "DataHandlingMessage2");

        assertEquals(description.getDataHandling1DroppedEvents(),
                     messageHitCount.getHitCount(DataHandlingMessage1DroppedEvent.class),
                     "DataHandlingMessage1DroppedEvent");
        assertEquals(description.getDataHandling2DroppedEvents(),
                     messageHitCount.getHitCount(DataHandlingMessage2DroppedEvent.class),
                     "DataHandlingMessage2DroppedEvent");
        assertEquals(description.getUnknownDroppedEvents(), messageHitCount.getHitCount(UnknownMessageDroppedEvent.class), "UnknownMessageDroppedEvent");
        assertEquals(description.getUnstructuredInputDroppedEvents(),
                     messageHitCount.getHitCount(UnstructuredInputDroppedEvent.class),
                     "UnstructuredInputDroppedEvent");

        messageHitCount.noMoreHits();
    }
}
