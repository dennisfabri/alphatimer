module org.lisasp.alphatimer.serialportlistener {
    exports org.lisasp.alphatimer.serialportlistener;
    exports org.lisasp.alphatimer.serialportlistener.mq;

    requires org.lisasp.alphatimer.serial;
    requires org.lisasp.alphatimer.api.ares.serial;
    requires org.lisasp.alphatimer.ares.serial;

    requires org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;
    requires org.lisasp.basics.spring.jms;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.jms;
    requires spring.context;

    requires artemis.core.client;
    requires nrjavaserial;

    requires java.annotation;
    requires java.validation;

    requires static lombok;
    requires artemis.server;
}
