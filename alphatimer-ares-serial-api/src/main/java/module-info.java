module org.lisasp.alphatimer.api.ares.serial {
    exports org.lisasp.alphatimer.api.ares.serial;
    exports org.lisasp.alphatimer.api.ares.serial.data;
    exports org.lisasp.alphatimer.api.ares.serial.events;
    exports org.lisasp.alphatimer.api.ares.serial.json;
    exports org.lisasp.alphatimer.api.ares.serial.events.dropped;
    exports org.lisasp.alphatimer.api.ares.serial.events.messages;
    exports org.lisasp.alphatimer.api.ares.serial.events.messages.enums;
    exports org.lisasp.alphatimer.api.ares.serial.events.messages.values;

    opens org.lisasp.alphatimer.api.ares.serial.events;
    opens org.lisasp.alphatimer.api.ares.serial.events.messages;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    requires static lombok;
}
