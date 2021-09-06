module org.lisasp.alphatimer.api.protocol {
    exports org.lisasp.alphatimer.api.protocol;
    exports org.lisasp.alphatimer.api.protocol.data;
    exports org.lisasp.alphatimer.api.protocol.events;
    exports org.lisasp.alphatimer.api.protocol.json;
    exports org.lisasp.alphatimer.api.protocol.events.dropped;
    exports org.lisasp.alphatimer.api.protocol.events.messages;
    exports org.lisasp.alphatimer.api.protocol.events.messages.enums;
    exports org.lisasp.alphatimer.api.protocol.events.messages.values;

    opens org.lisasp.alphatimer.api.protocol.events;
    opens org.lisasp.alphatimer.api.protocol.events.messages;

    requires org.lisasp.alphatimer.messaging;

    requires java.base;

    requires kotlin.stdlib;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    requires static lombok;
}
