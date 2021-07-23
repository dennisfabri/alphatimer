package org.lisasp.alphatimer.heats.current.domain;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.lisasp.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.api.LaneDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@ToString
public class Heat {

    private final String competition;
    private final int event;
    private final int heat;

    private LocalDateTime started;
    private ArrayList<Lane> lanes = new ArrayList<>();

    public Heat(HeatDto dto) {
        this(dto.getCompetition(), dto.getEvent(), dto.getHeat());
        this.started = dto.getStarted();

        for (LaneDto lane : dto.getLanes()) {
            lanes.add(new Lane(lane));
        }
    }

    public HeatDto createDto() {
        return new HeatDto(competition, event, heat, started, lanes.stream().map(lane -> lane.createDto()).toArray(LaneDto[]::new));
    }

    public void apply(StartMessage message) {
        this.started = message.getTimestamp();
    }

    public void apply(OfficialEndMessage message) {
        if (started == null) {
            this.started = message.getTimestamp();
        }
        for (Lane lane : lanes) {
            lane.finish();
        }
    }
}
