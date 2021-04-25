package de.dennisfabri.alphatimer.datatests;

import de.dennisfabri.alphatimer.collector.DataHandlingMessageAggregator;
import de.dennisfabri.alphatimer.collector.InputCollector;
import de.dennisfabri.alphatimer.legacy.LegacyTimeStorage;
import de.dennisfabri.alphatimer.legacy.model.Heat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LegacyDataTest {

    private InputCollector alphaTranslator;
    private LegacyTimeStorage timeStorage;

    private static final Testdata testdata = new Testdata();

    @BeforeAll
    static void prepareData() throws IOException {
        testdata.prepare();
    }

    @BeforeEach
    void prepare() {
        timeStorage = new LegacyTimeStorage();

        DataHandlingMessageAggregator aggregator = new DataHandlingMessageAggregator();
        aggregator.register(timeStorage);

        alphaTranslator = new InputCollector();
        alphaTranslator.register(aggregator);
    }

    @AfterEach
    void cleanUp() {
        alphaTranslator = null;
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
    @ValueSource(strings = {"DM2008Freitag", "DM2008Samstag", "DM2009", "DM2010",
            "JRP2019Freitag", "JRP2019Samstag", "JRP2019Sonntag",
            "DP2019Freitag", "DP2019Samstag",
            "DMM2019Freitag", "DMM2019Samstag", "DMM2019Sonntag"})
    void translate(String filename) throws IOException {
        byte[] data = testdata.readSerialInput(filename);

        for (byte d : data) {
            alphaTranslator.accept(d);
        }
        alphaTranslator.close();

        Heat[] actual = timeStorage.getHeats();

        testdata.writeToDebug(actual, filename);

        assertValid(testdata.readLegacyData(filename), actual);
    }
}
