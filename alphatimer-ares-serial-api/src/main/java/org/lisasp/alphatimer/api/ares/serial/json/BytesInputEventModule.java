package org.lisasp.alphatimer.api.ares.serial.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.lisasp.alphatimer.api.ares.serial.data.Bytes;
import org.lisasp.alphatimer.api.ares.serial.events.BytesInputEvent;

public class BytesInputEventModule extends SimpleModule {

    public BytesInputEventModule() {
        addSerializer(BytesInputEvent.class, new BytesInputEventSerializer());
        addDeserializer(BytesInputEvent.class, new BytesInputEventDeserializer());

        addSerializer(Bytes.class, new BytesSerializer());
        addDeserializer(Bytes.class, new BytesDeserializer());
    }

}
