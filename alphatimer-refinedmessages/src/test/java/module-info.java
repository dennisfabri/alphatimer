module org.lisasp.alphatimer.test.refinedmessages {
    requires org.lisasp.alphatimer.refinedmessages;

    opens org.lisasp.alphatimer.test.refinedmessages;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;
}
