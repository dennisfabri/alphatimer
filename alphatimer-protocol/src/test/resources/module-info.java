module org.lisasp.alphatimer.test.protocol {
    requires org.lisasp.alphatimer.protocol;

    opens org.lisasp.alphatimer.test.protocol;
    opens org.lisasp.alphatimer.test.protocol.utils;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;
}
