package org.lisasp.alphatimer.heats;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.TimeMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.UsedLanesMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedKindOfTime;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedTimeType;
import org.lisasp.alphatimer.heats.api.LaneStatus;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.api.LaneDto;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatRepository;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final LocalDateTime TIMESTAMP3 = LocalDateTime.of(2021, 6, 10, 10, 3);
    private static final LocalDateTime TIMESTAMP4 = LocalDateTime.of(2021, 6, 10, 10, 4);
    private static final LocalDateTime TIMESTAMP5 = LocalDateTime.of(2021, 6, 10, 10, 5);
    private static final LocalDateTime TIMESTAMP6 = LocalDateTime.of(2021, 6, 10, 10, 6);
    private static final LocalDateTime TIMESTAMP7 = LocalDateTime.of(2021, 6, 10, 10, 7);

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
    private final UsedLanesMessage usedLanesMessage = new UsedLanesMessage(TIMESTAMP2,
                                                                           COMPETITION,
                                                                           EVENT,
                                                                           HEAT,
                                                                           new UsedLanes(new boolean[]{true, true, true, true, true, true, true, true, false, false}));
    private final TimeMessage timeLane1Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111110,
                                                                 RefinedTimeType.Normal);
    private final TimeMessage timeLane2Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 2,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111120,
                                                                 RefinedTimeType.Normal);
    private final TimeMessage timeLane3Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 3,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111130,
                                                                 RefinedTimeType.Normal);
    private final TimeMessage timeLane4Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 4,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111140,
                                                                 RefinedTimeType.Normal);
    private final TimeMessage timeLane5Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 5,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111150,
                                                                 RefinedTimeType.Normal);
    private final TimeMessage timeLane6Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 6,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111160,
                                                                 RefinedTimeType.Normal);
    private final TimeMessage timeLane7Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 7,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111170,
                                                                 RefinedTimeType.Normal);
    private final TimeMessage timeLane8Message = new TimeMessage(TIMESTAMP3,
                                                                 COMPETITION,
                                                                 EVENT,
                                                                 HEAT,
                                                                 RefinedMessageType.Live,
                                                                 RefinedKindOfTime.Finish,
                                                                 (byte) 8,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 (byte) 1,
                                                                 111180,
                                                                 RefinedTimeType.Normal);
    private final OfficialEndMessage officialEndMessage = new OfficialEndMessage(TIMESTAMP4,
                                                                                 COMPETITION,
                                                                                 EVENT,
                                                                                 HEAT,
                                                                                 (byte) 1);

    private final LaneDto[] emptyLanes = new LaneDto[]{
            new LaneDto(1, 0, LaneStatus.NotUsed),
            new LaneDto(2, 0, LaneStatus.NotUsed),
            new LaneDto(3, 0, LaneStatus.NotUsed),
            new LaneDto(4, 0, LaneStatus.NotUsed),
            new LaneDto(5, 0, LaneStatus.NotUsed),
            new LaneDto(6, 0, LaneStatus.NotUsed),
            new LaneDto(7, 0, LaneStatus.NotUsed),
            new LaneDto(8, 0, LaneStatus.NotUsed)
    };

    private final LaneDto[] usedLanes = new LaneDto[]{
            new LaneDto(1, 0, LaneStatus.Used),
            new LaneDto(2, 0, LaneStatus.Used),
            new LaneDto(3, 0, LaneStatus.Used),
            new LaneDto(4, 0, LaneStatus.Used),
            new LaneDto(5, 0, LaneStatus.Used),
            new LaneDto(6, 0, LaneStatus.Used),
            new LaneDto(7, 0, LaneStatus.Used),
            new LaneDto(8, 0, LaneStatus.Used)
    };

    private final LaneDto[] lane1Lanes = new LaneDto[]{
            new LaneDto(1, 111110, LaneStatus.Started),
            new LaneDto(2, 0, LaneStatus.Used),
            new LaneDto(3, 0, LaneStatus.Used),
            new LaneDto(4, 0, LaneStatus.Used),
            new LaneDto(5, 0, LaneStatus.Used),
            new LaneDto(6, 0, LaneStatus.Used),
            new LaneDto(7, 0, LaneStatus.Used),
            new LaneDto(8, 0, LaneStatus.Used)
    };

    private final LaneDto[] completeLanes = new LaneDto[]{
            new LaneDto(1, 111110, LaneStatus.Finished),
            new LaneDto(2, 111120, LaneStatus.Finished),
            new LaneDto(3, 111130, LaneStatus.Finished),
            new LaneDto(4, 111140, LaneStatus.Finished),
            new LaneDto(5, 111150, LaneStatus.Finished),
            new LaneDto(6, 111160, LaneStatus.Finished),
            new LaneDto(7, 111170, LaneStatus.Finished),
            new LaneDto(8, 111180, LaneStatus.Finished)
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
    void unusedTimeLane1Message() {
        heatsService.accept(timeLane1Message);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP3, emptyLanes));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void timeLane1Message() {
        heatsService.accept(usedLanesMessage);
        heatsService.accept(timeLane1Message);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP2, usedLanes));
        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP2, lane1Lanes));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void officialEndMessage() {
        heatsService.accept(officialEndMessage);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP4, emptyLanes));
        verifyNoMoreInteractions(listener);
    }

    @Test
    void emptyHeat() {
        heatsService.accept(startMessage);
        heatsService.accept(officialEndMessage);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP1, emptyLanes));
        verifyNoMoreInteractions(listener);
    }

    @Test
    @Disabled
    void completeHeat() {
        heatsService.accept(startMessage);
        heatsService.accept(usedLanesMessage);
        heatsService.accept(timeLane1Message);
        heatsService.accept(timeLane2Message);
        heatsService.accept(timeLane3Message);
        heatsService.accept(timeLane4Message);
        heatsService.accept(timeLane5Message);
        heatsService.accept(timeLane6Message);
        heatsService.accept(timeLane7Message);
        heatsService.accept(timeLane8Message);
        heatsService.accept(officialEndMessage);

        verify(listener, times(1)).accept(new HeatDto(COMPETITION, EVENT, HEAT, TIMESTAMP1, emptyLanes));
        verifyNoMoreInteractions(listener);
    }
}
