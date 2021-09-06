module org.lisasp.alphatimer.test.datatests {
    requires org.lisasp.alphatimer.datatests;

    opens org.lisasp.alphatimer.test.datatests;

    requires org.lisasp.alphatimer.protocol;
    requires org.lisasp.alphatimer.refinedmessages;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;
}
