module org.lisasp.alphatimer.test.messagesstorage {
    requires org.lisasp.alphatimer.messagesstorage;

    opens org.lisasp.alphatimer.test.messagesstorage;

    requires org.lisasp.alphatimer.spring.jpa;
    requires org.lisasp.alphatimer.api.protocol;

    requires spring.boot;
    requires spring.test;
    requires spring.beans;

    requires spring.boot.test;
    requires spring.boot.starter.test;
    requires spring.boot.starter;
    requires spring.boot.test.autoconfigure;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;
}
