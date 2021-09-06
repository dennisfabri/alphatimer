module org.lisasp.alphatimer.test.jpa {
    requires org.lisasp.alphatimer.spring.jpa;

    opens org.lisasp.alphatimer.test.spring.jpa;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.engine;

    requires static lombok;

    requires spring.boot.test.autoconfigure;
    requires spring.context;
    requires spring.test;
    requires spring.boot.test;
    requires spring.boot.autoconfigure;
    requires spring.data.jpa;
    requires spring.beans;
    requires spring.core;
}
