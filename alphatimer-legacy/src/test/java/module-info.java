module org.lisasp.alphatimer.test.legacy {
    requires org.lisasp.alphatimer.legacy;

    opens org.lisasp.alphatimer.test.legacy;
    opens org.lisasp.alphatimer.test.legacy.model;
    exports org.lisasp.alphatimer.test.legacy;

    requires org.lisasp.alphatimer.api.protocol;

    requires cloning;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;
}
