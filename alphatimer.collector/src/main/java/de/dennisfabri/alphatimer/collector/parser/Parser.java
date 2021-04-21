package de.dennisfabri.alphatimer.collector.parser;

import de.dennisfabri.alphatimer.api.events.DataInputEvent;
import de.dennisfabri.alphatimer.api.events.dropped.UnknownMessageDroppedEvent;
import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;

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
