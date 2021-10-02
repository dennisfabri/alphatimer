module org.lisasp.alphatimer.test.api.protocol {
    requires org.lisasp.alphatimer.api.protocol;

    opens org.lisasp.alphatimer.test.api.protocol.data;
    opens org.lisasp.alphatimer.test.api.protocol.events;
    opens org.lisasp.alphatimer.test.api.protocol.events.dropped;
    opens org.lisasp.alphatimer.test.api.protocol.events.messages;
    opens org.lisasp.alphatimer.test.api.protocol.events.messages.enums;
    opens org.lisasp.alphatimer.test.api.protocol.events.messages.values;

    requires spring.jms;
    requires jakarta.jms.api;

    requires org.lisasp.basics.spring.jms;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;

    requires transitive org.mockito;
    requires transitive net.bytebuddy;
    requires transitive net.bytebuddy.agent;
}
