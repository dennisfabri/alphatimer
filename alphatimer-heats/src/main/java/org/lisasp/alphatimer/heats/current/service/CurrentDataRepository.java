package org.lisasp.alphatimer.heats.current.service;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.heats.api.LaneStatus;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.api.LaneDto;
import org.lisasp.alphatimer.heats.current.entity.HeatEntity;
import org.lisasp.alphatimer.heats.current.entity.LaneEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CurrentDataRepository {

    private final CurrentHeatRepository heatRepository;
    private final CurrentLaneRepository laneRepository;

    public Optional<HeatDto> findHeat(String competition, int event, int heat) {
        Optional<HeatEntity> entity = heatRepository.findByCompetitionAndEventAndHeat(competition, event, heat);
        if (entity.isEmpty()) {
            return Optional.empty();
        }

        List<LaneEntity> lanes = laneRepository.findAllByCompetitionAndEventAndHeat(competition, event, heat);

        return Optional.of(entity.get().toDto(lanes.stream().map(lane -> lane.toDto()).toArray(LaneDto[]::new)));
    }

    public HeatDto createHeat(String competition, int event, int heat, LocalDateTime timestamp, int laneCount) {
        HeatEntity entity = new HeatEntity(competition, event, heat, timestamp);
        heatRepository.save(entity);

        LaneEntity[] lanes = new LaneEntity[laneCount];
        for (int x = 0; x < laneCount; x++) {
            int laneNumber = x + 1;
            lanes[x] = createLaneEntity(competition, event, heat, laneNumber);
        }

        return entity.toDto(Arrays.stream(lanes).map(l -> l.toDto()).toArray(LaneDto[]::new));
    }

    private LaneEntity createLaneEntity(String competition, int event, int heat, int laneNumber) {
        LaneEntity entity = new LaneEntity(competition, event, heat, laneNumber, 0, LaneStatus.NotUsed);
        laneRepository.save(entity);
        return entity;
    }

    public void save(HeatDto heat) {
        updateHeat(heat);
        updateLanes(heat);
    }

    private void updateLanes(HeatDto heat) {
        Set<Integer> laneNumbers = Arrays.stream(heat.getLanes()).map(l -> l.getNumber()).collect(Collectors.toSet());
        List<LaneEntity> lanes = laneRepository.findAllByCompetitionAndEventAndHeat(heat.getCompetition(), heat.getEvent(), heat.getHeat());
        Map<Integer, LaneEntity> usedLanes = lanes.stream().filter(l -> laneNumbers.contains(l.getNumber())).collect(Collectors.toMap(l -> l.getNumber(),
                                                                                                                                      l -> l));
        LaneEntity[] unusedLanes = lanes.stream().filter(l -> !laneNumbers.contains(l.getNumber())).toArray(LaneEntity[]::new);

        Arrays.stream(unusedLanes).forEach(l -> laneRepository.delete(l));

        for (LaneDto lane : heat.getLanes()) {
            LaneEntity laneEntity = usedLanes.get(lane.getNumber());
            if (laneEntity == null) {
                laneEntity = new LaneEntity();
                laneEntity.setCompetition(heat.getCompetition());
                laneEntity.setEvent(heat.getEvent());
                laneEntity.setHeat(heat.getHeat());
                laneEntity.setNumber(lane.getNumber());
            }
            laneEntity.setTimeInMillis(lane.getTimeInMillis());
            laneEntity.setStatus(lane.getStatus());

            laneRepository.save(laneEntity);
        }
    }

    private void updateHeat(HeatDto heat) {
        HeatEntity entity = heatRepository.findByCompetitionAndEventAndHeat(heat.getCompetition(), heat.getEvent(), heat.getHeat()).get();
        entity.update(heat);
        heatRepository.save(entity);
    }
}
