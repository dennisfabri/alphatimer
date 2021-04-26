package de.dennisfabri.alphatimer.protocol.parser;

import de.dennisfabri.alphatimer.api.protocol.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.protocol.events.messages.Message;
import de.dennisfabri.alphatimer.protocol.exceptions.InvalidDataException;

interface MessageParser {
    boolean isKnownMessageFormat(byte[] data);

    Message parse(byte[] data) throws InvalidDataException;

    DataInputEvent createDropMessage(byte[] data, InvalidDataException ide);
}
