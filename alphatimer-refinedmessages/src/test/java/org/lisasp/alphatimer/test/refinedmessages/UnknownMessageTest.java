package org.lisasp.alphatimer.test.refinedmessages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.ares.serial.events.messages.DataHandlingMessage;
import org.lisasp.alphatimer.api.ares.serial.events.messages.enums.*;
import org.lisasp.alphatimer.api.ares.serial.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessageListener;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedUnknownMessage;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class UnknownMessageTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2021, 6, 1, 10, 0);

    private DataHandlingMessageRefiner refiner;
    private RefinedMessageListener listener;

    @BeforeEach
    void prepare() {
        listener = Mockito.mock(RefinedMessageListener.class);

        refiner = new DataHandlingMessageRefiner();
        refiner.register(listener);
    }

    @Test
    void valid() {
        final byte lapCount = 2;
        final byte event = 3;
        final byte heat = 4;

        refiner.accept(new DataHandlingMessage(
                TIMESTAMP,
                "TestWK",
                "1", "2",
                MessageType.UnknownValue7,
                KindOfTime.Empty,
                TimeType.Empty,
                new UsedLanes(new boolean[]{true, true, false, false, false, false, false, false, false, false}),
                lapCount,
                event,
                heat,
                (byte) 0,
                RankInfo.Normal,
                (byte) 0,
                (byte) 0,
                0,
                TimeInfo.Normal,
                TimeMarker.Empty));

        verify(listener, times(1)).accept(new DroppedUnknownMessage(TIMESTAMP, "TestWK", MessageType.UnknownValue7, KindOfTime.Empty, TimeType.Empty,
                                                                    "1100000000",
                                                                    lapCount,
                                                                    event,
                                                                    heat,
                                                                    (byte) 0,
                                                                    RankInfo.Normal,
                                                                    (byte) 0,
                                                                    (byte) 0,
                                                                    0,
                                                                    TimeInfo.Normal,
                                                                    TimeMarker.Empty));
        verifyNoMoreInteractions(listener);
    }

}
