package org.lisasp.alphatimer.api.ares.serial.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

class BytesInputEventSerializer extends StdSerializer<BytesInputEvent> {
    public BytesInputEventSerializer() {
        this(null);
    }

    public BytesInputEventSerializer(Class<BytesInputEvent> t) {
        super(t);
    }

    @Override
    public void serialize(
            BytesInputEvent value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeFieldName("competition");
        jgen.writeString(value.getCompetition());
        jgen.writeFieldName("timestamp");
        jgen.writeString(value.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME));
        jgen.writeFieldName("data");
        jgen.writeBinary(value.getData());
        jgen.writeEndObject();
    }
}
