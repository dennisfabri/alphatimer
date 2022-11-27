module org.lisasp.alphatimer.test.api.ares.serial {
    requires org.lisasp.alphatimer.api.ares.serial;

    opens org.lisasp.alphatimer.test.api.ares.serial.data;
    opens org.lisasp.alphatimer.test.api.ares.serial.events;
    opens org.lisasp.alphatimer.test.api.ares.serial.events.dropped;
    opens org.lisasp.alphatimer.test.api.ares.serial.events.messages;
    opens org.lisasp.alphatimer.test.api.ares.serial.events.messages.enums;
    opens org.lisasp.alphatimer.test.api.ares.serial.events.messages.values;

    requires spring.jms;
    requires jakarta.messaging;

    requires org.lisasp.basics.spring.jms;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;

    requires transitive org.mockito;
    requires transitive net.bytebuddy;
    requires transitive net.bytebuddy.agent;

    requires static lombok;
}
