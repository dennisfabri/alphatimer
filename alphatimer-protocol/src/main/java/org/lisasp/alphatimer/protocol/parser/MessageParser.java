package org.lisasp.alphatimer.protocol.parser;

import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.Message;
import org.lisasp.alphatimer.protocol.exceptions.InvalidDataException;

interface MessageParser {
    boolean isKnownMessageFormat(byte[] data);

    Message parse(byte[] data) throws InvalidDataException;

    DataInputEvent createDropMessage(byte[] data, InvalidDataException ide);
}
