package org.lisasp.alphatimer.heats.service;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.heats.api.enums.HeatStatus;
import org.lisasp.alphatimer.heats.api.enums.LaneStatus;
import org.lisasp.alphatimer.heats.api.enums.Penalty;
import org.lisasp.alphatimer.heats.api.HeatDto;
import org.lisasp.alphatimer.heats.api.LaneDto;
import org.lisasp.alphatimer.heats.entity.HeatEntity;
import org.lisasp.alphatimer.heats.entity.LaneEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataRepository {

    private final HeatRepository heatRepository;

    @Transactional(readOnly = true)
    public Optional<HeatDto> findHeat(String competition, int event, int heat) {
        Optional<HeatEntity> entity = heatRepository.findByCompetitionAndEventAndHeat(competition, event, heat);
        if (entity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(entity.get().toDto());
    }

    @Transactional
    public HeatDto createHeat(String competition, int event, int heat, int laneCount, int lap, int lapCount) {
        HeatEntity entity = new HeatEntity(competition, event, heat, HeatStatus.Open, lapCount);
        entity.setLanes(new ArrayList<>());
        heatRepository.save(entity);

        List<LaneEntity> lanes = new ArrayList<>();
        for (int x = 0; x < laneCount; x++) {
            int laneNumber = x + 1;
            LaneEntity lane = createLaneEntity(entity, laneNumber, lap);
            entity.getLanes().add(lane);
        }

        heatRepository.save(entity);

        return entity.toDto();
    }

    @Transactional
    public void save(HeatDto heat) {
        updateHeat(heat);
        updateLanes(heat);
    }

    private LaneEntity createLaneEntity(HeatEntity heat, int laneNumber, int lap) {
        return new LaneEntity(heat, laneNumber, 0, LaneStatus.NotUsed, Penalty.None, lap);
    }

    private void updateLanes(HeatDto heat) {
        HeatEntity heatEntity = heatRepository.findByCompetitionAndEventAndHeat(heat.getCompetition(), heat.getEvent(), heat.getHeat()).orElseThrow();

        Set<Integer> laneNumbers = Arrays.stream(heat.getLanes()).map(l -> l.getNumber()).collect(Collectors.toSet());
        List<LaneEntity> lanes = heatEntity.getLanes();
        Map<Integer, LaneEntity> usedLanes = lanes.stream().filter(l -> laneNumbers.contains(l.getNumber())).collect(Collectors.toMap(l -> l.getNumber(),
                                                                                                                                      l -> l));
        heatEntity.getLanes().removeAll(lanes.stream().filter(l -> !laneNumbers.contains(l.getNumber())).collect(Collectors.toList()));

        for (LaneDto lane : heat.getLanes()) {
            LaneEntity laneEntity = usedLanes.get(lane.getNumber());
            if (laneEntity == null) {
                laneEntity = new LaneEntity();
                laneEntity.setHeat(heatEntity);
                laneEntity.setNumber(lane.getNumber());
            }
            laneEntity.setTimeInMillis(lane.getTimeInMillis());
            laneEntity.setStatus(lane.getStatus());
            laneEntity.setLap(lane.getLap());
        }

        heatRepository.save(heatEntity);
    }

    private void updateHeat(HeatDto heat) {
        HeatEntity entity = heatRepository.findByCompetitionAndEventAndHeat(heat.getCompetition(), heat.getEvent(), heat.getHeat()).get();
        entity.update(heat);
        heatRepository.save(entity);
    }
}
