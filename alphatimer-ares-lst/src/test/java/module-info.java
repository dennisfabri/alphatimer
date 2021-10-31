module org.lisasp.alphatimer.test.ares.lst {
    requires org.lisasp.alphatimer.ares.lst;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;

    requires transitive org.mockito;
    requires transitive net.bytebuddy;
    requires transitive net.bytebuddy.agent;

    requires static lombok;
}
