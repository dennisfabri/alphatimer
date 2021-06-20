package org.lisasp.alphatimer.livetiming.data.entity;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class LiveHeat {

    private final HashMap<Integer, Lane> lanes;

    public List<Lane> getLanes(int minLaneCount) {
        int actualLaneCount = Math.max(minLaneCount, lanes.keySet().stream().min((o1, o2) -> o2 - o1).orElse(0));

        List<Lane> myLanes = new ArrayList<>();
        for (int laneNumber = 1; laneNumber <= actualLaneCount; laneNumber++) {
            myLanes.add(lanes.computeIfAbsent(laneNumber, x -> new Lane(x)));
        }
        return myLanes;
    }


}
