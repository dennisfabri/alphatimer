package org.lisasp.alphatimer.protocol.parser;

import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.api.protocol.events.messages.Message;
import org.lisasp.alphatimer.protocol.exceptions.InvalidDataException;

import java.time.LocalDateTime;

interface MessageParser {
    boolean isKnownMessageFormat(byte[] data);

    Message parse(BytesInputEvent event) throws InvalidDataException;

    DataInputEvent createDropMessage(BytesInputEvent event, InvalidDataException ide);
}
