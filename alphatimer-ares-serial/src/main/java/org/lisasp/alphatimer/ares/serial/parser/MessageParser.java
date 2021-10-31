package org.lisasp.alphatimer.ares.serial.parser;

import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;
import org.lisasp.alphatimer.api.ares.serial.events.DataInputEvent;
import org.lisasp.alphatimer.api.ares.serial.events.messages.Message;
import org.lisasp.alphatimer.ares.serial.exceptions.InvalidDataException;

interface MessageParser {
    boolean isKnownMessageFormat(byte[] data);

    Message parse(BytesInputEvent event) throws InvalidDataException;

    DataInputEvent createDropMessage(BytesInputEvent event, InvalidDataException ide);
}
