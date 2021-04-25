package de.dennisfabri.alphatimer.collector.parser;

import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.protocol.events.dropped.DataHandlingMessage2DroppedEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage2;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Message;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeInfo;
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeMarker;
import de.dennisfabri.alphatimer.collector.Characters;
import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;
import de.dennisfabri.alphatimer.collector.utils.ByteArrayUtils;

import java.util.Arrays;
import java.util.NoSuchElementException;

class DataHandlingMessage2Parser implements MessageParser {

    private final ByteArrayUtils byteArrayUtils = new ByteArrayUtils();

    public boolean isKnownMessageFormat(byte[] data) {
        if (data.length != 21 && data.length != 22) {
            return false;
        }
        return data[1] == Characters.STX_StartOfText && data[2] == Characters.BS_CursorHome && data[3] == Characters.LF_LineFeed && data[7] == Characters.STX_StartOfText;
    }

    public Message parse(byte[] data) throws InvalidDataException {
        return new DataHandlingMessage2(
                new String(data),
                getLane(data),
                getCurrentLap(data),
                getTimeInMillis(data),
                getTimeInfo(data),
                getTimeMarker(data));
    }

    // Field I: Index 4
    private byte getLane(byte[] data) throws InvalidDataException {
        return byteArrayUtils.toByteValue(data[4]);
    }

    // Field J: Index 5-6
    private byte getCurrentLap(byte[] data) throws InvalidDataException {
        return byteArrayUtils.toByteValue(data[5], data[6]);
    }

    // Field K: Index 8-18
    private int getTimeInMillis(byte[] data) throws InvalidDataException {
        try {
            return byteArrayUtils.extractTimeInMillis(data, 8, determineTimeLength(data));
        } catch (NumberFormatException nfe) {
            throw new InvalidDataException(String.format("Data at indices 8-18 must contain a valid time: '%s'",
                                                         byteArrayUtils.toString(data, 8, determineTimeLength(data))));
        }
    }

    // Field K (included): Index 8-18
    private TimeMarker getTimeMarker(byte[] data) {
        String text = byteArrayUtils.toString(data, 8, determineTimeLength(data)).trim();
        if (text.equals("DNS")) {
            return TimeMarker.DidNotStart;
        }
        if (text.equals("DNF")) {
            return TimeMarker.DidNotFinish;
        }
        if (text.equals("-.--")) {
            return TimeMarker.Empty;
        }
        if (text.startsWith("+ ")) {
            return TimeMarker.Plus;
        }
        if (text.startsWith("-")) {
            return TimeMarker.Minus;
        }
        return TimeMarker.Empty;
    }

    private int determineTimeLength(byte[] data) {
        return data.length - 10;
    }

    // Field K: Index 19
    private TimeInfo getTimeInfo(byte[] data) throws InvalidDataException {
        char decodedValue = byteArrayUtils.toCharacter(data[data.length - 2]);
        try {
            return Arrays.stream(TimeInfo.values()).filter(v -> v.getValue() == decodedValue).findFirst().orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new InvalidDataException(
                    String.format("%d must be the ascii-representation of a 'time info'.",
                                  data[19]), exception);
        }
    }

    @Override
    public DataInputEvent createDropMessage(byte[] data, InvalidDataException ide) {
        return new DataHandlingMessage2DroppedEvent(ide.getMessage(), data);
    }
}
