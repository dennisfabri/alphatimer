package org.lisasp.alphatimer.api.protocol.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class BytesInputEventDeserializer extends StdDeserializer<BytesInputEvent> {

    public BytesInputEventDeserializer() {
        this(null);
    }

    public BytesInputEventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BytesInputEvent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String competition = node.get("competition").textValue();
        LocalDateTime timestamp = LocalDateTime.parse(node.get("timestamp").textValue(), DateTimeFormatter.ISO_DATE_TIME);
        byte[] data = node.get("data").binaryValue();
        return new BytesInputEvent(timestamp, competition, data);
    }
}
