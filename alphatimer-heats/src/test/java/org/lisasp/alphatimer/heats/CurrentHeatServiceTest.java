package org.lisasp.alphatimer.heats;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedTimeType;
import org.lisasp.alphatimer.heats.api.LaneStatus;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.api.LaneDto;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatRepository;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest()
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CurrentHeatServiceTest {

    private static final LocalDateTime TIMESTAMP1 = LocalDateTime.of(2021, 6, 10, 10, 1);
    private static final LocalDateTime TIMESTAMP2 = LocalDateTime.of(2021, 6, 10, 10, 2);
    private static final int TIME_OF_DAY_MILLIS = 10 * 60 * 60 * 1000;

    private static final String COMPETITION = "TestWK";
    private static final short EVENT = 1;
    private static final byte HEAT = 2;

    @Autowired
    private CurrentHeatService heatsService;
    @Autowired
    private CurrentHeatRepository heatEntities;

    private final StartMessage startMessage = new StartMessage(TIMESTAMP1,
                                                               COMPETITION,
                                                               EVENT,
                                                               HEAT,
                                                               RefinedMessageType.Live,
                                                               (byte) 0,
                                                               (byte) 1,
                                                               TIME_OF_DAY_MILLIS,
                                                               RefinedTimeType.Normal);
    private final OfficialEndMessage officialEndMessage = new OfficialEndMessage(TIMESTAMP2,
                                                                                 COMPETITION,
                                                                                 EVENT,
                                                                                 HEAT,
                                                                                 (byte) 1);

    private final LaneDto[] emptyLanes = new LaneDto[] {
            new LaneDto(1, 0 , LaneStatus.NotUsed),
            new LaneDto(2, 0 , LaneStatus.NotUsed),
            new LaneDto(3, 0 , LaneStatus.NotUsed),
            new LaneDto(4, 0 , LaneStatus.NotUsed),
            new LaneDto(5, 0 , LaneStatus.NotUsed),
            new LaneDto(6, 0 , LaneStatus.NotUsed),
            new LaneDto(7, 0 , LaneStatus.NotUsed),
            new LaneDto(8, 0 , LaneStatus.NotUsed)
    };

    private HeatListener listener;


    private class TestFailedException extends RuntimeException {
    }

    @BeforeEach
    void initialize() {
        listener = Mockito.mock(HeatListener.class);
        heatsService.register(listener);
    }

    @Test
    void startMessage() {
        heatsService.accept(startMessage);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP1, emptyLanes));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void officialEndMessage() {
        heatsService.accept(officialEndMessage);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP2, emptyLanes));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void startAndOfficialEndMessage() {
        heatsService.accept(startMessage);
        heatsService.accept(officialEndMessage);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP1, emptyLanes));
        // verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP2, emptyLanes));
        verifyNoMoreInteractions(listener);
    }
}
