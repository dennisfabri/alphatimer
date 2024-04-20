package org.lisasp.alphatimer.test.datatests;

import com.thoughtworks.xstream.XStream;
import lombok.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lisasp.alphatimer.api.ares.serial.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.datatests.TestData;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.legacy.dto.Heat;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.ares.serial.MessageAggregator;
import org.lisasp.alphatimer.ares.serial.MessageConverter;
import org.lisasp.alphatimer.test.datatests.testdoubles.DateTimeFacadeTestDouble;
import org.lisasp.alphatimer.test.legacy.TestLegacyRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LegacyDataTest {

    private static TestData testData;
    private InputCollector inputCollector;
    private LegacyService timeStorage;
    private XStream xstream;

    @BeforeEach
    void prepare() {
        testData = new TestData();

        timeStorage = new LegacyService(new TestLegacyRepository(),"TestWK");

        DateTimeFacade dateTimeFacade = new DateTimeFacadeTestDouble(LocalDateTime.of(2021, 6, 1, 10, 0));

        DataHandlingMessageAggregator aggregator = new MessageAggregator();
        aggregator.register(timeStorage);

        MessageConverter messageConverter = new MessageConverter();
        messageConverter.register(aggregator);

        inputCollector = new InputCollector("TestWK", dateTimeFacade);
        inputCollector.register(messageConverter);

        xstream = LegacyXStreamUtil.getXStream();
    }

    @AfterEach
    void cleanUp() {
        inputCollector = null;
        timeStorage = null;
    }

    private void assertValid(Heat[] expected, Heat[] actual) {
        assertNotNull(actual);
        for (int x = 0; x < Math.min(expected.length, actual.length); x++) {
            assertEquals(expected[x],
                         actual[x],
                         String.format("Difference at %d/%d", expected[x].getEvent(), expected[x].getHeat()));
        }
        assertEquals(expected.length, actual.length);
    }

    @ParameterizedTest
    @ValueSource(strings = {"DM2008-Freitag", "DM2008-Samstag", "DM2009", "DM2010",
            "DP2019-Freitag", "DP2019-Samstag",
            "DMM2019-Freitag", "DMM2019-Samstag", "DMM2019-Sonntag",
            "DEM2021-Samstag", "DEM2021-Sonntag"})
    void translate(String filename) throws IOException {
        byte[] data = testData.readSerialInput(filename);

        for (byte d : data) {
            inputCollector.accept(d);
        }
        inputCollector.close();
        Heat[] actual = timeStorage.getHeats();

        testData.writeToDebug(actual, filename);
        Heat[] expected = readLegacyData(filename);
        HashSet<EventAndHeat> duplicates = findDuplicates(expected);

        assertValid(cleanData(expected, duplicates), cleanData(actual, duplicates));
    }

    private Heat[] cleanData2(Heat[] input, HashSet<EventAndHeat> duplicates) {
        List<Heat> tmp = new ArrayList<>();
        for (int x = 0; x < input.length; x++) {
            Heat heat = input[x];
            if (!duplicates.contains(new EventAndHeat(heat.getEvent(), heat.getHeat()))) {
                tmp.add(heat);
            }
        }
        Heat[] expected = tmp.stream().sorted().toArray(Heat[]::new);
        for (int x = 0; x < expected.length; x++) {
            expected[x].setId("" + (x + 1));
        }
        return expected;
    }

    private Heat[] cleanData(Heat[] input, HashSet<EventAndHeat> duplicates) {
        Heat[] expected = Arrays.stream(input).filter(heat -> heat.getEvent() > 0 && heat.getHeat() > 0 && !duplicates.contains(new EventAndHeat(heat.getEvent(),
                                                                                                                                                 heat.getHeat()))).sorted().toArray(
                Heat[]::new);
        for (int x = 0; x < expected.length; x++) {
            expected[x].setId("" + (x + 1));
        }
        return expected;
    }

    private Heat[] readLegacyData(String filename) throws IOException {
        return (Heat[]) xstream.fromXML(testData.readLegacyData(filename));
    }

    /**
     * The original implementation in AlphaServer may produce the same event/heat multiple times. This behaviour is not desired,
     * therefore the original data for such heats should not be used in tests. These heats are eliminated prior to comparison;
     */
    private HashSet<EventAndHeat> findDuplicates(Heat[] input) {
        HashSet<EventAndHeat> found = new HashSet<>();
        HashSet<EventAndHeat> duplicates = new HashSet<>();
        for (Heat heat : input) {
            EventAndHeat eventAndHeat = new EventAndHeat(heat.getEvent(), heat.getHeat());
            if (found.contains(eventAndHeat)) {
                duplicates.add(eventAndHeat);
            } else {
                found.add(eventAndHeat);
            }
        }
        return duplicates;
    }

    @Value
    private static class EventAndHeat {
        private final int event;
        private final int heat;
    }
}
