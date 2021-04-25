package de.dennisfabri.alphatimer.datatests;

import de.dennisfabri.alphatimer.api.refinedmessages.RefinedMessage;
import de.dennisfabri.alphatimer.api.refinedmessages.accepted.*;
import de.dennisfabri.alphatimer.api.refinedmessages.dropped.*;
import de.dennisfabri.alphatimer.collector.DataHandlingMessageAggregator;
import de.dennisfabri.alphatimer.collector.InputCollector;
import de.dennisfabri.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Hashtable;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

class RefinedMessagesDataTest {

    private static final Hashtable<Class, Integer> createHashtable(int didNotFinish,
                                                                   int didNotStart,
                                                                   int finish,
                                                                   int officialEnd,
                                                                   int readyToStart,
                                                                   int start,
                                                                   int takeOverTime,
                                                                   int usedLanes) {
        Hashtable<Class, Integer> hashtable = new Hashtable<>();

        hashtable.put(DidNotFinishMessage.class, didNotFinish);
        hashtable.put(DidNotStartMessage.class, didNotStart);
        hashtable.put(TimeMessage.class, finish);
        hashtable.put(OfficialEndMessage.class, officialEnd);
        hashtable.put(ReadyToStartMessage.class, readyToStart);
        hashtable.put(StartMessage.class, start);
        hashtable.put(TakeOverTimeMessage.class, takeOverTime);
        hashtable.put(UsedLanesMessage.class, usedLanes);

        return hashtable;
    }

    private static void extendHashtable(Hashtable<Class, Integer> hashtable,
                                        int didNotFinish,
                                        int didNotStart,
                                        int finish,
                                        int officialEnd,
                                        int readyToStart,
                                        int start,
                                        int takeOverTime,
                                        int usedLanes,
                                        int droppedUnknown) {

        hashtable.put(DroppedDidNotFinishMessage.class, didNotFinish);
        hashtable.put(DroppedDidNotStartMessage.class, didNotStart);
        hashtable.put(DroppedTimeMessage.class, finish);
        hashtable.put(DroppedOfficialEndMessage.class, officialEnd);
        hashtable.put(DroppedReadyToStartMessage.class, readyToStart);
        hashtable.put(DroppedStartMessage.class, start);
        hashtable.put(DroppedTakeOverTimeMessage.class, takeOverTime);
        // hashtable.put(DroppedUsedLanesMessage.class, usedLanes);

        hashtable.put(DroppedUnknownMessage.class, droppedUnknown);
    }

    private static final Hashtable<String, Hashtable<Class, Integer>> testData = new Hashtable<>();

    private static final Hashtable<Class, Integer> EMPTY = new Hashtable<>();

    private static int getValue(String name, Class message) {
        return testData.getOrDefault(name, EMPTY).getOrDefault(message, 0);
    }

    static {
        testData.put("DM2008Freitag", createHashtable(0, 0, 899, 0, 67, 67, 373, 0));
        extendHashtable(testData.get("DM2008Freitag"), 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testData.put("DM2008Samstag", createHashtable(0, 0, 1177, 0, 154, 145, 0, 0));
        extendHashtable(testData.get("DM2008Samstag"), 0, 0, 0, 0, 0, 0, 0, 0, 0);

        testData.put("DM2009", createHashtable(0, 0, 5739, 8, 658, 638, 1254, 0));
        extendHashtable(testData.get("DM2009"), 0, 0, 2, 0, 0, 2, 0, 0, 0);

        testData.put("DM2010", createHashtable(0, 0, 1621, 2, 147, 142, 525, 0));
        extendHashtable(testData.get("DM2010"), 0, 0, 0, 0, 0, 0, 0, 0, 0);

        testData.put("JRP2019Freitag", createHashtable(0, 0, 0, 0, 2, 1, 0, 0));
        extendHashtable(testData.get("JRP2019Freitag"), 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testData.put("JRP2019Samstag", createHashtable(0, 47, 846, 97, 109, 100, 0, 0));
        extendHashtable(testData.get("JRP2019Samstag"), 0, 0, 0, 0, 0, 0, 0, 0, 754);
        testData.put("JRP2019Sonntag", createHashtable(2, 29, 533, 51, 69, 57, 0, 0));
        extendHashtable(testData.get("JRP2019Sonntag"), 0, 0, 0, 0, 0, 0, 0, 0, 408);

        testData.put("DP2019Freitag", createHashtable(0, 0, 1226, 86, 96, 94, 0, 0));
        extendHashtable(testData.get("DP2019Freitag"), 0, 0, 0, 0, 0, 0, 0, 0, 2);
        testData.put("DP2019Samstag", createHashtable(0, 0, 1220, 68, 75, 73, 0, 0));
        extendHashtable(testData.get("DP2019Samstag"), 0, 0, 0, 0, 0, 0, 0, 0, 0);

        testData.put("DMM2019Freitag", createHashtable(0, 0, 9, 0, 3, 2, 0, 0));
        extendHashtable(testData.get("DMM2019Freitag"), 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testData.put("DMM2019Samstag", createHashtable(0, 0, 1535, 120, 139, 134, 0, 0));
        extendHashtable(testData.get("DMM2019Samstag"), 0, 0, 3, 0, 0, 0, 0, 0, 5);
        testData.put("DMM2019Sonntag", createHashtable(0, 0, 1219, 140, 163, 164, 0, 0));
        extendHashtable(testData.get("DMM2019Sonntag"), 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    private static final boolean verbose = false;

    private InputCollector alphaTranslator;
    private Consumer<RefinedMessage> listener;
    private DataHandlingMessageRefiner refiner;

    private static final Testdata testdata = new Testdata();

    @BeforeAll
    static void prepareData() throws IOException {
        testdata.prepare();
    }

    @BeforeEach
    void prepare() {
        listener = (Consumer<RefinedMessage>) mock(Consumer.class);

        refiner = new DataHandlingMessageRefiner();
        refiner.register(listener);


        alphaTranslator = new InputCollector();
        alphaTranslator.register(new DataHandlingMessageAggregator(refiner));
        if (verbose) {
            refiner.register(event -> {
                if (event instanceof DroppedRefinedMessage) {
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

    @ParameterizedTest
    @ValueSource(strings = {
            "DM2008Freitag", "DM2008Samstag",
            "DM2009",
            "DM2010",
            "JRP2019Freitag", "JRP2019Samstag", "JRP2019Sonntag",
            "DP2019Freitag", "DP2019Samstag",
            "DMM2019Freitag", "DMM2019Samstag", "DMM2019Sonntag"})
    void test(String name) throws IOException {
        byte[] data = testdata.readSerialInput(name);

        for (byte d : data) {
            alphaTranslator.accept(d);
        }
        alphaTranslator.close();


        verify(listener, times(getValue(name, DidNotFinishMessage.class))).accept(Mockito.any(DidNotFinishMessage.class));
        verify(listener, times(getValue(name, DidNotStartMessage.class))).accept(Mockito.any(DidNotStartMessage.class));
        verify(listener, times(getValue(name, TimeMessage.class))).accept(Mockito.any(TimeMessage.class));
        verify(listener, times(getValue(name, OfficialEndMessage.class))).accept(Mockito.any(OfficialEndMessage.class));
        verify(listener, times(getValue(name, ReadyToStartMessage.class))).accept(Mockito.any(ReadyToStartMessage.class));
        verify(listener, times(getValue(name, StartMessage.class))).accept(Mockito.any(StartMessage.class));
        verify(listener, times(getValue(name, TakeOverTimeMessage.class))).accept(Mockito.any(TakeOverTimeMessage.class));
        verify(listener, times(getValue(name, UsedLanesMessage.class))).accept(Mockito.any(UsedLanesMessage.class));

        verify(listener, times(getValue(name, DroppedDidNotFinishMessage.class))).accept(Mockito.any(DroppedDidNotFinishMessage.class));
        verify(listener, times(getValue(name, DroppedDidNotStartMessage.class))).accept(Mockito.any(DroppedDidNotStartMessage.class));
        verify(listener, times(getValue(name, DroppedTimeMessage.class))).accept(Mockito.any(DroppedTimeMessage.class));
        verify(listener, times(getValue(name, DroppedOfficialEndMessage.class))).accept(Mockito.any(DroppedOfficialEndMessage.class));
        verify(listener, times(getValue(name, DroppedReadyToStartMessage.class))).accept(Mockito.any(DroppedReadyToStartMessage.class));
        verify(listener, times(getValue(name, DroppedStartMessage.class))).accept(Mockito.any(DroppedStartMessage.class));
        verify(listener, times(getValue(name, DroppedTakeOverTimeMessage.class))).accept(Mockito.any(DroppedTakeOverTimeMessage.class));

        verify(listener, times(getValue(name, DroppedUnknownMessage.class))).accept(Mockito.any(DroppedUnknownMessage.class));

        verifyNoMoreInteractions(listener);
    }
}
