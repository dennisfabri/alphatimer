module org.lisasp.alphatimer.test.messagesstorage {
    requires org.lisasp.alphatimer.messagesstorage;

    opens org.lisasp.alphatimer.test.messagesstorage;
    exports org.lisasp.alphatimer.test.messagesstorage;

    requires org.lisasp.alphatimer.api.ares.serial;

    requires cloning;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;
}
