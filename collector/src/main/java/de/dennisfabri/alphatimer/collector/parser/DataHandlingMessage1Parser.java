package de.dennisfabri.alphatimer.collector.parser;

import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.DataHandlingMessage1DroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage1;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Message;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.KindOfTime;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.MessageType;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.RankInfo;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeType;
import de.dennisfabri.alphatimer.api.protocol.events.messages.values.UsedLanes;
import de.dennisfabri.alphatimer.collector.Characters;
import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;
import de.dennisfabri.alphatimer.collector.utils.BitUtils;
import de.dennisfabri.alphatimer.collector.utils.ByteArrayUtils;

import java.util.Arrays;
import java.util.NoSuchElementException;

class DataHandlingMessage1Parser implements MessageParser {

    private final BitUtils bitUtils = new BitUtils();
    private final ByteArrayUtils byteArrayUtils = new ByteArrayUtils();

    public boolean isKnownMessageFormat(byte[] data) {
        return data.length == 20 && data[1] == Characters.STX_StartOfText && data[2] == Characters.BS_CursorHome && data[15] == Characters.SPACE && data[16] == Characters.SPACE;
    }

    public Message parse(byte[] data) throws InvalidDataException {
        return new DataHandlingMessage1(
                new String(data),
                getMessageType(data),
                getKindOfTime(data),
                getTimeType(data),
                getUsedLanes(data),
                getLapCount(data),
                getEvent(data),
                getHeat(data),
                getRank(data),
                getRankInfo(data)
        );
    }

    @Override
    public DataInputEvent createDropMessage(byte[] data, InvalidDataException ide) {
        return new DataHandlingMessage1DroppedEvent(ide.getMessage(), data);
    }

    // Field A: Index 3
    private MessageType getMessageType(byte[] data) throws InvalidDataException {
        byte decodedValue = byteArrayUtils.toByteValue(data[3]);
        try {
            return Arrays.stream(MessageType.values()).filter(v -> v.getValue() == decodedValue).findFirst().orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new InvalidDataException(
                    String.format("DataHandling Message 1: %d must be the ascii-representation of a 'message type'.",
                                  data[3]), exception);
        }
    }

    // Field B: Index 4
    private KindOfTime getKindOfTime(byte[] data) throws InvalidDataException {
        char decodedValue = byteArrayUtils.toCharacter(data[4]);
        try {
            return Arrays.stream(KindOfTime.values()).filter(v -> v.getValue() == decodedValue).findFirst().orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new InvalidDataException(
                    String.format("DataHandling Message 1: %d must be the ascii-representation of a 'kind of time'.",
                                  data[4]), exception);
        }
    }

    // Field C: Index 5
    private TimeType getTimeType(byte[] data) throws InvalidDataException {
        char decodedValue = byteArrayUtils.toCharacter(data[5]);
        try {
            return Arrays.stream(TimeType.values()).filter(v -> v.getValue() == decodedValue).findFirst().orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new InvalidDataException(
                    String.format("DataHandling Message 1: %d must be the ascii-representation of a 'time type'.",
                                  data[5]), exception);
        }
    }

    // Field D: Index 6-7
    private UsedLanes getUsedLanes(byte[] data) throws InvalidDataException {
        return new UsedLanes(bitUtils.extractUsedLanes(data[6], data[7]));
    }

    // Field E: Index 8-9
    private byte getLapCount(byte[] data) throws InvalidDataException {
        return byteArrayUtils.toByteValue(data[8], data[9]);
    }

    // Field F: Index 10-12
    private short getEvent(byte[] data) throws InvalidDataException {
        return byteArrayUtils.toShortValue(data[10], data[11], data[12]);
    }

    // Field G: Index 13-14
    private byte getHeat(byte[] data) throws InvalidDataException {
        return byteArrayUtils.toByteValue(data[13], data[14]);
    }

    // Field H: Index 17-18
    private byte getRank(byte[] data) throws InvalidDataException {
        if (data[17] == 0x44 && data[18] == 0x20) {
            // In some cases (e.g. KindOfTime == TakeOverTime) no rank is submitted => falling back to 0
            return 0;
        }
        return byteArrayUtils.toByteValue(data[17], data[18]);
    }

    // Field H (included): Index 17-18
    private RankInfo getRankInfo(byte[] data) {
        if (data[17] == 0x44 && data[18] == 0x20) {
            return RankInfo.Disqualified;
        }
        return RankInfo.Normal;
    }
}
