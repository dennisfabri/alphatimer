package org.lisasp.alphatimer.test.datatests;

import lombok.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessageListener;
import org.lisasp.alphatimer.api.refinedmessages.accepted.*;
import org.lisasp.alphatimer.api.refinedmessages.dropped.*;
import org.lisasp.alphatimer.datatests.TestData;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.ares.serial.MessageAggregator;
import org.lisasp.alphatimer.ares.serial.MessageConverter;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.test.datatests.testdoubles.DateTimeFacadeTestDouble;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RefinedMessagesDataTest {

    private static final boolean verbose = false;

    private InputCollector inputCollector;
    private HitCounter counter;

    @Value
    static class TestDescription {
        private final String name;
        private final int didNotFinish;
        private final int didNotStart;
        private final int time;
        private final int officialEnd;
        private final int readyToStart;
        private final int start;
        private final int takeOverTime;
        private final int usedLanes;
        private final int droppedDidNotFinish;
        private final int droppedDidNotStart;
        private final int droppedTime;
        private final int droppedOfficialEnd;
        private final int droppedReadyToStart;
        private final int droppedStart;
        private final int droppedTakeOverTime;
        private final int droppedUsedLanes;
        private final int droppedUnknown;

        @Override
        public String toString() {
            return name;
        }
    }

    static TestDescription[] provideTestDescriptions() {
        return new TestDescription[]{
                new TestDescription("DM2008-Freitag", 0, 0, 899, 0, 67, 67, 373, 1406, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                new TestDescription("DM2008-Samstag", 0, 0, 1177, 0, 154, 145, 0, 1476, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                new TestDescription("DM2009", 0, 0, 5739, 8, 658, 638, 1254, 8011, 0, 0, 2, 0, 0, 2, 0, 0, 0),
                new TestDescription("DM2010", 0, 0, 1621, 2, 147, 142, 525, 2435, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                new TestDescription("DMM2019-Freitag", 0, 0, 9, 0, 3, 2, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                new TestDescription("DMM2019-Samstag", 0, 0, 1535, 120, 139, 134, 0, 1811, 0, 0, 3, 0, 0, 0, 0, 0, 5),
                new TestDescription("DMM2019-Sonntag", 0, 0, 1219, 140, 163, 164, 0, 1546, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                new TestDescription("DP2019-Freitag", 0, 0, 1226, 86, 96, 94, 0, 1416, 0, 0, 0, 0, 0, 0, 0, 0, 2),
                new TestDescription("DP2019-Samstag", 0, 0, 1220, 68, 75, 73, 0, 1368, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                new TestDescription("DEM2021-Samstag", 0, 0, 414, 23, 30, 29, 0, 576, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                new TestDescription("DEM2021-Sonntag", 0, 0, 764, 45, 67, 57, 0, 1054, 0, 0, 0, 0, 0, 0, 0, 0, 5),
        };
    }

    @BeforeEach
    void prepare() {
        counter = new HitCounter();
        RefinedMessageListener listener = refinedMessage -> counter.increaseHitCount(refinedMessage.getClass());

        DataHandlingMessageRefiner refiner = new DataHandlingMessageRefiner();
        refiner.register(listener);

        DateTimeFacade dateTimeFacade = new DateTimeFacadeTestDouble(LocalDateTime.of(2021, 6, 1, 10, 0));

        MessageAggregator messageAggregator = new MessageAggregator();
        messageAggregator.register(refiner);

        MessageConverter messageConverter = new MessageConverter();
        messageConverter.register(messageAggregator);
        if (verbose) {
            refiner.register(event -> {
                if (event instanceof DroppedRefinedMessage) {
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
    }

    @ParameterizedTest
    @MethodSource("provideTestDescriptions")
    void test(TestDescription description) throws IOException {
        byte[] data = new TestData().readSerialInput(description.getName());

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();

        assertEquals(description.getDidNotFinish(), counter.getHitCount(DidNotFinishMessage.class), "DidNotFinishMessage");
        assertEquals(description.getDidNotStart(), counter.getHitCount(DidNotStartMessage.class), "DidNotStartMessage");
        assertEquals(description.getTime(), counter.getHitCount(TimeMessage.class), "TimeMessage");
        assertEquals(description.getOfficialEnd(), counter.getHitCount(OfficialEndMessage.class), "OfficialEndMessage");
        assertEquals(description.getReadyToStart(), counter.getHitCount(ReadyToStartMessage.class), "ReadyToStartMessage");
        assertEquals(description.getStart(), counter.getHitCount(StartMessage.class), "StartMessage");
        assertEquals(description.getTakeOverTime(), counter.getHitCount(TakeOverTimeMessage.class), "TakeOverTimeMessage");
        assertEquals(description.getUsedLanes(), counter.getHitCount(UsedLanesMessage.class), "UsedLanesMessage");

        assertEquals(description.getDroppedDidNotFinish(), counter.getHitCount(DroppedDidNotFinishMessage.class), "DroppedDidNotFinishMessage");
        assertEquals(description.getDroppedDidNotStart(), counter.getHitCount(DroppedDidNotStartMessage.class), "DroppedDidNotStartMessage");
        assertEquals(description.getDroppedTime(), counter.getHitCount(DroppedTimeMessage.class), "DroppedTimeMessage");
        assertEquals(description.getDroppedOfficialEnd(), counter.getHitCount(DroppedOfficialEndMessage.class), "DroppedOfficialEndMessage");
        assertEquals(description.getDroppedReadyToStart(), counter.getHitCount(DroppedReadyToStartMessage.class), "DroppedReadyToStartMessage");
        assertEquals(description.getDroppedStart(), counter.getHitCount(DroppedStartMessage.class), "DroppedStartMessage");
        assertEquals(description.getDroppedTakeOverTime(), counter.getHitCount(DroppedTakeOverTimeMessage.class), "DroppedTakeOverTimeMessage");

        assertEquals(description.getDroppedUnknown(), counter.getHitCount(DroppedUnknownMessage.class), "DroppedUnknownMessage");

        counter.noMoreHits();
    }
}
