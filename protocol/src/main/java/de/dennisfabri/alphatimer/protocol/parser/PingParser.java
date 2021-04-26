package de.dennisfabri.alphatimer.protocol.parser;

import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Message;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Ping;
import de.dennisfabri.alphatimer.protocol.Characters;
import de.dennisfabri.alphatimer.protocol.exceptions.InvalidDataException;

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
