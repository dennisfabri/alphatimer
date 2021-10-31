package org.lisasp.alphatimer.api.ares.serial.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.lisasp.alphatimer.api.ares.serial.data.Bytes;

import java.io.IOException;

class BytesSerializer extends StdSerializer<Bytes> {
    public BytesSerializer() {
        this(null);
    }

    public BytesSerializer(Class<Bytes> t) {
        super(t);
    }

    @Override
    public void serialize(
            Bytes value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeFieldName("data");
        jgen.writeBinary(value.getDataCopy());
        jgen.writeEndObject();
    }
}
