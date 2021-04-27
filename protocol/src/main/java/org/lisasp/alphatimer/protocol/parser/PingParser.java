package org.lisasp.alphatimer.protocol.parser;

import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.Message;
import org.lisasp.alphatimer.api.protocol.events.messages.Ping;
import org.lisasp.alphatimer.protocol.Characters;
import org.lisasp.alphatimer.protocol.exceptions.InvalidDataException;

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
