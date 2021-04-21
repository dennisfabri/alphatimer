package de.dennisfabri.alphatimer.messagesstorage;

import de.dennisfabri.alphatimer.api.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.api.events.messages.enums.*;
import de.dennisfabri.alphatimer.api.events.messages.values.UsedLanes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MessagesTest {

    @Autowired
    AresMessageRepository repository;

    private Messages messages;
    private DataHandlingMessage messageHeat1Lane1;
    private DataHandlingMessage messageHeat1Lane2;
    private DataHandlingMessage messageHeat2Lane1;
    private DataHandlingMessage messageHeat2Lane2;

    private final String messageHeat1Lane1String = "DataHandlingMessage(messageType=ReadyToStart, kindOfTime=Empty, timeType=Empty, usedLanes=UsedLanes(lanes=[true, false, true, false, true, false, true, false, true, false]), lapCount=0, event=1, heat=1, rank=0, rankInfo=Normal, lane=1, currentLap=0, timeInMillis=0, timeInfo=Normal, timeMarker=Empty)";
    private final String messageHeat1Lane2String = "DataHandlingMessage(messageType=ReadyToStart, kindOfTime=Empty, timeType=Empty, usedLanes=UsedLanes(lanes=[true, false, true, false, true, false, true, false, true, false]), lapCount=0, event=1, heat=1, rank=0, rankInfo=Normal, lane=2, currentLap=0, timeInMillis=0, timeInfo=Normal, timeMarker=Empty)";
    private final String messageHeat2Lane1String = "DataHandlingMessage(messageType=ReadyToStart, kindOfTime=Empty, timeType=Empty, usedLanes=UsedLanes(lanes=[true, false, true, false, true, false, true, false, true, false]), lapCount=0, event=1, heat=2, rank=0, rankInfo=Normal, lane=1, currentLap=0, timeInMillis=0, timeInfo=Normal, timeMarker=Empty)";
    private final String messageHeat2Lane2String = "DataHandlingMessage(messageType=ReadyToStart, kindOfTime=Empty, timeType=Empty, usedLanes=UsedLanes(lanes=[true, false, true, false, true, false, true, false, true, false]), lapCount=0, event=1, heat=2, rank=0, rankInfo=Normal, lane=2, currentLap=0, timeInMillis=0, timeInfo=Normal, timeMarker=Empty)";

    @BeforeEach
    void prepare() {
        messages = new Messages(repository);
        messageHeat1Lane1 = new DataHandlingMessage(MessageType.ReadyToStart,
                                                    KindOfTime.Empty,
                                                    TimeType.Empty,
                                                    new UsedLanes(getLanes()),
                                                    (byte) 0,
                                                    (short) 1,
                                                    (byte) 1,
                                                    (byte) 0,
                                                    RankInfo.Normal,
                                                    (byte) 1,
                                                    (byte) 0,
                                                    0,
                                                    TimeInfo.Normal,
                                                    TimeMarker.Empty);
        messageHeat1Lane2 = new DataHandlingMessage(MessageType.ReadyToStart,
                                                    KindOfTime.Empty,
                                                    TimeType.Empty,
                                                    new UsedLanes(getLanes()),
                                                    (byte) 0,
                                                    (short) 1,
                                                    (byte) 1,
                                                    (byte) 0,
                                                    RankInfo.Normal,
                                                    (byte) 2,
                                                    (byte) 0,
                                                    0,
                                                    TimeInfo.Normal,
                                                    TimeMarker.Empty);

        messageHeat2Lane1 = new DataHandlingMessage(MessageType.ReadyToStart,
                                                    KindOfTime.Empty,
                                                    TimeType.Empty,
                                                    new UsedLanes(getLanes()),
                                                    (byte) 0,
                                                    (short) 1,
                                                    (byte) 2,
                                                    (byte) 0,
                                                    RankInfo.Normal,
                                                    (byte) 1,
                                                    (byte) 0,
                                                    0,
                                                    TimeInfo.Normal,
                                                    TimeMarker.Empty);
        messageHeat2Lane2 = new DataHandlingMessage(MessageType.ReadyToStart,
                                                    KindOfTime.Empty,
                                                    TimeType.Empty,
                                                    new UsedLanes(getLanes()),
                                                    (byte) 0,
                                                    (short) 1,
                                                    (byte) 2,
                                                    (byte) 0,
                                                    RankInfo.Normal,
                                                    (byte) 2,
                                                    (byte) 0,
                                                    0,
                                                    TimeInfo.Normal,
                                                    TimeMarker.Empty);
    }

    private BitSet getLanes() {
        BitSet bs = new BitSet();
        for (int x = 0; x < 10; x++) {
            bs.set(x, x % 2 == 0);
        }
        return bs;
    }

    @AfterEach
    void cleanup() {
        messages = null;
        messageHeat1Lane1 = null;
        messageHeat1Lane2 = null;
        messageHeat2Lane1 = null;
        messageHeat2Lane2 = null;
    }

    @Test
    void empty() {
        assertEquals(0, messages.size());
    }

    @Test
    void put1Message() {
        messages.put(messageHeat1Lane1);

        assertEquals(1, messages.size());

        assertEquals(1, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).size());
        assertEquals(messageHeat1Lane1String, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).get(0).toString());
    }

    @Test
    void put2Messages() {
        messages.put(messageHeat1Lane1);
        messages.put(messageHeat1Lane2);

        assertEquals(2, messages.size());

        assertEquals(2, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).size());
        assertEquals(messageHeat1Lane1String, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).get(0).toString());
        assertEquals(messageHeat1Lane2String, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).get(1).toString());
    }

    @Test
    void put3Messages() {
        messages.put(messageHeat1Lane1);
        messages.put(messageHeat1Lane2);
        messages.put(messageHeat2Lane1);

        assertEquals(3, messages.size());

        assertEquals(2, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).size());
        assertEquals(messageHeat1Lane1String, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).get(0).toString());
        assertEquals(messageHeat1Lane2String, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).get(1).toString());

        assertEquals(1, messages.get(messageHeat2Lane1.getEvent(), messageHeat2Lane1.getHeat()).size());
        assertEquals(messageHeat2Lane1String, messages.get(messageHeat2Lane1.getEvent(), messageHeat2Lane1.getHeat()).get(0).toString());
    }

    @Test
    void put4Messages() {
        messages.put(messageHeat1Lane1);
        messages.put(messageHeat1Lane2);
        messages.put(messageHeat2Lane1);
        messages.put(messageHeat2Lane2);

        assertEquals(4, messages.size());

        assertEquals(2, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).size());
        assertEquals(messageHeat1Lane1String, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).get(0).toString());
        assertEquals(messageHeat1Lane2String, messages.get(messageHeat1Lane1.getEvent(), messageHeat1Lane1.getHeat()).get(1).toString());

        assertEquals(2, messages.get(messageHeat2Lane1.getEvent(), messageHeat2Lane1.getHeat()).size());
        assertEquals(messageHeat2Lane1String, messages.get(messageHeat2Lane1.getEvent(), messageHeat2Lane1.getHeat()).get(0).toString());
        assertEquals(messageHeat2Lane2String, messages.get(messageHeat2Lane1.getEvent(), messageHeat2Lane1.getHeat()).get(1).toString());
    }
}
