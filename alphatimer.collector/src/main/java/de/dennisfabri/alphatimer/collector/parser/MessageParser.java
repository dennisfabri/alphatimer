package de.dennisfabri.alphatimer.collector.parser;

import de.dennisfabri.alphatimer.api.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.events.messages.Message;
import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;

interface MessageParser {
    boolean isKnownMessageFormat(byte[] data);

    Message parse(byte[] data) throws InvalidDataException;

    DataInputEvent createDropMessage(byte[] data, InvalidDataException ide);
}
