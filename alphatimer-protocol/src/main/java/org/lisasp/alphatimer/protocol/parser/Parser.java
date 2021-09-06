package org.lisasp.alphatimer.protocol.parser;

import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;
import org.lisasp.alphatimer.api.protocol.events.DataInputEvent;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnknownMessageDroppedEvent;
import org.lisasp.alphatimer.api.protocol.events.dropped.UnstructuredInputDroppedEvent;
import org.lisasp.alphatimer.protocol.exceptions.InvalidDataException;

public class Parser {

    private final MessageParser[] parsers = new MessageParser[]{
            new DataHandlingMessage1Parser(), new DataHandlingMessage2Parser(), new PingParser()
    };

    public DataInputEvent parse(BytesInputEvent event) {
        if (!event.checkIfMessage()) {
            return new UnstructuredInputDroppedEvent(event.getTimestamp(), event.getCompetition(), event.getData());
        }
        for (MessageParser parser : parsers) {
            if (parser.isKnownMessageFormat(event.getData())) {
                try {
                    return parser.parse(event);
                } catch (InvalidDataException ide) {
                    return parser.createDropMessage(event, ide);
                }
            }
        }

        return new UnknownMessageDroppedEvent(event.getTimestamp(), event.getCompetition(), event.getData());
    }
}
