package de.dennisfabri.alphatimer.collector.parser;

import de.dennisfabri.alphatimer.api.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.events.messages.Message;
import de.dennisfabri.alphatimer.api.events.messages.Ping;
import de.dennisfabri.alphatimer.collector.Characters;
import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;

class PingParser implements MessageParser {

    @Override
    public boolean isKnownMessageFormat(byte[] data) {
        return data.length == 7 && data[1] == Characters.DC2_Periphery && data[2] == 0x39 && data[3] == Characters.DC4_Command;
    }

    @Override
    public Message parse(byte[] data) {
        return new Ping(new byte[]{data[data.length - 3], data[data.length - 2]});
    }

    @Override
    public DataInputEvent createDropMessage(byte[] data, InvalidDataException ide) {
        return null;
    }
}
