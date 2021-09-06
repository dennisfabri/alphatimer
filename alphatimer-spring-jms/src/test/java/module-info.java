module org.lisasp.alphatimer.test.jms {
    requires org.lisasp.alphatimer.spring.jms;

    opens org.lisasp.alphatimer.test.spring.jms;
    opens org.lisasp.alphatimer.test.spring.jms.data;

    requires spring.jms;
    requires spring.beans;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires spring.boot;

    requires jakarta.jms.api;

    requires mockrunner.core;
    requires mockrunner.jms;
    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires spring.test;
    requires spring.boot.test;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.engine;

    requires static lombok;
}
