module org.lisasp.alphatimer.test.messaging {
    requires org.lisasp.alphatimer.messaging;

    opens org.lisasp.alphatimer.test.messaging;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
}
