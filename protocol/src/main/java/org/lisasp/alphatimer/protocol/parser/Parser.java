package org.lisasp.alphatimer.protocol.parser;

import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import org.lisasp.alphatimer.protocol.exceptions.InvalidDataException;

public class Parser {

    private final MessageParser[] parsers = new MessageParser[]{
            new DataHandlingMessage1Parser(), new DataHandlingMessage2Parser(), new PingParser()
    };

    public DataInputEvent parse(byte[] data) {
        for (MessageParser parser : parsers) {
            if (parser.isKnownMessageFormat(data)) {
                try {
                    return parser.parse(data);
                } catch (InvalidDataException ide) {
                    return parser.createDropMessage(data, ide);
                }
            }
        }

        return new UnknownMessageDroppedEvent(data);
    }
}
