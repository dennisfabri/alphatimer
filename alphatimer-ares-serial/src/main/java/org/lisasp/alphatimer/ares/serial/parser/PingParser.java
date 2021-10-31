package org.lisasp.alphatimer.ares.serial.parser;

import org.lisasp.alphatimer.api.ares.serial.Characters;
import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;
import org.lisasp.alphatimer.api.ares.serial.events.DataInputEvent;
import org.lisasp.alphatimer.api.ares.serial.events.messages.Message;
import org.lisasp.alphatimer.api.ares.serial.events.messages.Ping;
import org.lisasp.alphatimer.ares.serial.exceptions.InvalidDataException;

class PingParser implements MessageParser {

    @Override
    public boolean isKnownMessageFormat(byte[] data) {
        return data.length == 7 && data[1] == Characters.DC2_Periphery && data[2] == 0x39 && data[3] == Characters.DC4_Command;
    }

    @Override
    public Message parse(BytesInputEvent event) {
        byte[] data = event.getData();
        return new Ping(event.getTimestamp(), event.getCompetition(), new byte[]{data[data.length - 3], data[data.length - 2]});
    }

    @Override
    public DataInputEvent createDropMessage(BytesInputEvent event, InvalidDataException ide) {
        return null;
    }
}
