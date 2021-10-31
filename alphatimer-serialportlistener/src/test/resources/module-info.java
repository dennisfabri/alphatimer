module org.lisasp.alphatimer.test.serialportlistener {
    requires transitive org.lisasp.alphatimer.serialportlistener;

    opens org.lisasp.alphatimer.test.serialportlistener;

    requires org.lisasp.alphatimer.api.ares.serial;

    requires org.lisasp.basics.jre;

    requires spring.beans;
    requires spring.boot;
    requires spring.boot.test;
    requires spring.context;
    requires spring.test;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;

    requires static lombok;
}
