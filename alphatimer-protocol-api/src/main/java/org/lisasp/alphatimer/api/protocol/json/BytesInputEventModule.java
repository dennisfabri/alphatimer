package org.lisasp.alphatimer.api.protocol.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.lisasp.alphatimer.api.protocol.data.Bytes;
import org.lisasp.alphatimer.api.protocol.events.BytesInputEvent;

public class BytesInputEventModule extends SimpleModule {

    public BytesInputEventModule() {
        addSerializer(BytesInputEvent.class, new BytesInputEventSerializer());
        addDeserializer(BytesInputEvent.class, new BytesInputEventDeserializer());

        addSerializer(Bytes.class, new BytesSerializer());
        addDeserializer(Bytes.class, new BytesDeserializer());
    }

}
