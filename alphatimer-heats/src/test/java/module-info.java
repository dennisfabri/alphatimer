module org.lisasp.alphatimer.test.heats {
    requires org.lisasp.alphatimer.heats;

    opens org.lisasp.alphatimer.test.heats;
    opens org.lisasp.alphatimer.test.heats.domain;

    requires org.lisasp.alphatimer.api.protocol;
    requires org.lisasp.alphatimer.api.refinedmessages;

    requires org.lisasp.basics.spring.jpa;
    requires org.lisasp.basics.jre;

    requires cloning;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;

    requires static lombok;
}
