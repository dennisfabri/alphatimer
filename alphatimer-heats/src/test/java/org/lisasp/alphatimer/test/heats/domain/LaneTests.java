package org.lisasp.alphatimer.test.heats.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.lisasp.alphatimer.api.refinedmessages.accepted.TimeMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedKindOfTime;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedTimeType;
import org.lisasp.alphatimer.heats.api.LaneDto;
import org.lisasp.alphatimer.heats.api.enums.LaneStatus;
import org.lisasp.alphatimer.heats.api.enums.Penalty;
import org.lisasp.alphatimer.heats.domain.Heat;
import org.lisasp.alphatimer.heats.domain.Lane;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LaneTests {

    private static final LocalDateTime Timestamp = LocalDateTime.of(2021, 6, 19, 12,23,45);

    private static final LaneDto NotUsedDto = new LaneDto(1, 0, LaneStatus.NotUsed, Penalty.None, 0);
    private static final LaneDto UsedDto = new LaneDto(1, 0, LaneStatus.Used, Penalty.None, 0);
    private static final LaneDto ValidTimeDto = new LaneDto(1, 1234, LaneStatus.Used, Penalty.None, 0);

    private static final LaneDto UnexpectedTimeDto = new LaneDto(1, 1234, LaneStatus.NotUsed, Penalty.None, 1);
    private static final LaneDto InvalidTimeDto = new LaneDto(1, -1234, LaneStatus.Used, Penalty.None, 1);
    private static final LaneDto UnexpectedLapDto = new LaneDto(1, 0, LaneStatus.Used, Penalty.None, 1);

    @BeforeEach
    void prepare() {
    }

    @Test
    void initialize() {
        Lane lane = new Lane(1);
        assertEquals(lane, lane);
    }

    private static LaneDto[] createDtos() {
        return new LaneDto[] {UsedDto, NotUsedDto, ValidTimeDto};
    }

    @ParameterizedTest
    @MethodSource("createDtos")
    void fromValidDto(LaneDto dto) {
        Lane lane = new Lane(dto);
        assertEquals(dto, lane.createDto());
    }

    @Test
    void fromUnexpectedTimeDto() {
        Lane lane = new Lane(UnexpectedTimeDto);
        assertNotEquals(UnexpectedTimeDto, lane.createDto());
        assertEquals(NotUsedDto, lane.createDto());
    }

    @Test
    void fromInvalidTimeDto() {
        Lane lane = new Lane(InvalidTimeDto);
        assertNotEquals(InvalidTimeDto, lane.createDto());
        assertEquals(UsedDto, lane.createDto());
    }

    @Test
    void fromUnexpectedLapto() {
        Lane lane = new Lane(UnexpectedLapDto);
        assertNotEquals(UnexpectedLapDto, lane.createDto());
        assertEquals(UsedDto, lane.createDto());
    }

    @Test
    void touch() {
        Lane lane = new Lane(UsedDto);
        lane.touch(new TimeMessage(Timestamp, "TestWK", (short)1, (byte)1, RefinedMessageType.Live, RefinedKindOfTime.SplitTime,  (byte)1,  (byte)1,  (byte)2,  (byte)1, 12345, RefinedTimeType.Normal));

        LaneDto dto = lane.createDto();
        assertEquals(12345, dto.getTimeInMillis());
        assertEquals(1, dto.getLap());
    }

    @Test
    void touchWithoutTimeOnEmpty() {
        Lane lane = new Lane(UsedDto);
        lane.touch(new TimeMessage(Timestamp, "TestWK", (short)1, (byte)1, RefinedMessageType.Live, RefinedKindOfTime.SplitTime,  (byte)1,  (byte)1,  (byte)2,  (byte)1, 0, RefinedTimeType.Normal));

        LaneDto dto = lane.createDto();
        assertEquals(0, dto.getTimeInMillis());
        assertEquals(0, dto.getLap());
    }

    @Test
    void touchWithoutTime() {
        Lane lane = new Lane(UsedDto);
        lane.touch(new TimeMessage(Timestamp, "TestWK", (short)1, (byte)1, RefinedMessageType.Live, RefinedKindOfTime.SplitTime,  (byte)1,  (byte)1,  (byte)2,  (byte)1, 12345, RefinedTimeType.Normal));
        lane.touch(new TimeMessage(Timestamp, "TestWK", (short)1, (byte)1, RefinedMessageType.Live, RefinedKindOfTime.SplitTime,  (byte)1,  (byte)1,  (byte)2,  (byte)1, 0, RefinedTimeType.Normal));

        LaneDto dto = lane.createDto();
        assertEquals(12345, dto.getTimeInMillis());
        assertEquals(1, dto.getLap());
    }

    @Test
    void touchWithInvalidTimeOnEmpty() {
        Lane lane = new Lane(UsedDto);
        lane.touch(new TimeMessage(Timestamp, "TestWK", (short)1, (byte)1, RefinedMessageType.Live, RefinedKindOfTime.SplitTime,  (byte)1,  (byte)1,  (byte)2,  (byte)1, -12345, RefinedTimeType.Normal));

        LaneDto dto = lane.createDto();
        assertEquals(0, dto.getTimeInMillis());
        assertEquals(0, dto.getLap());
    }

    @Test
    void touchWithInvalidTime() {
        Lane lane = new Lane(UsedDto);
        lane.touch(new TimeMessage(Timestamp, "TestWK", (short)1, (byte)1, RefinedMessageType.Live, RefinedKindOfTime.SplitTime,  (byte)1,  (byte)1,  (byte)2,  (byte)1, 12345, RefinedTimeType.Normal));
        lane.touch(new TimeMessage(Timestamp, "TestWK", (short)1, (byte)1, RefinedMessageType.Live, RefinedKindOfTime.SplitTime,  (byte)1,  (byte)1,  (byte)2,  (byte)1, -12345, RefinedTimeType.Normal));

        LaneDto dto = lane.createDto();
        assertEquals(12345, dto.getTimeInMillis());
        assertEquals(1, dto.getLap());
    }
}
