package org.lisasp.alphatimer.datatests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.legacy.LegacyTimeStorage;
import org.lisasp.alphatimer.legacy.model.Heat;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageAggregator;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LegacyDataTest {

    private InputCollector alphaTranslator;
    private LegacyTimeStorage timeStorage;

    @BeforeAll
    static void prepareData() throws IOException {
        new TestData().prepare();
    }

    @AfterAll
    static void cleanup() throws IOException {
        new TestData().cleanup();
    }


    @BeforeEach
    void prepare() {
        timeStorage = new LegacyTimeStorage();

        DateTimeFacade datetime = Mockito.mock(DateTimeFacade.class);
        Mockito.when(datetime.now()).thenReturn(LocalDateTime.of(2021, 6, 1, 10, 0));

        DataHandlingMessageAggregator aggregator = new MessageAggregator(datetime, "TestWK");
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
        byte[] data = new TestData().readSerialInput(filename);

        for (byte d : data) {
            alphaTranslator.accept(d);
        }
        alphaTranslator.close();

        Heat[] actual = timeStorage.getHeats();

        new TestData().writeToDebug(actual, filename);

        assertValid(new TestData().readLegacyData(filename), actual);
    }
}
