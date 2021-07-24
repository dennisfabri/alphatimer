package org.lisasp.alphatimer.heats.current.domain;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.TimeMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.UsedLanesMessage;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.api.LaneDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public void start(StartMessage message) {
        this.started = message.getTimestamp();
    }

    public void usedLanes(UsedLanesMessage message) {
        UsedLanes usedLanes = UsedLanes.fromValue(message.getUsedLanes());
        lanes.forEach(l -> l.used(usedLanes.isUsed(l.getNumber()-1)));
    }

    public void touch(TimeMessage message) {
        assureStarted(message);

        int laneNumber = message.getLane();
        Lane lane = lanes.stream().filter(l -> l.getNumber() == laneNumber).findFirst().orElseThrow(() -> new NoSuchElementException(String.format("%s %d %d: Lane %d not found.", competition, event, heat, laneNumber)));
        lane.apply(message);
    }

    public void finish(OfficialEndMessage message) {
        assureStarted(message);
        for (Lane lane : lanes) {
            lane.finish();
        }
    }

    private void assureStarted(RefinedMessage message) {
        if (started == null) {
            this.started = message.getTimestamp();
        }
    }
}
