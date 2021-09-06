package org.lisasp.alphatimer.api.protocol.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.lisasp.alphatimer.api.protocol.data.Bytes;

import java.io.IOException;

class BytesDeserializer extends StdDeserializer<Bytes> {

    public BytesDeserializer() {
        this(null);
    }

    public BytesDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Bytes deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        byte[] data = node.get("data").binaryValue();
        return new Bytes(data);
    }
}
