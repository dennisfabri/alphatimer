module org.lisasp.alphatimer.heats {
    exports org.lisasp.alphatimer.heats;
    exports org.lisasp.alphatimer.heats.api;
    exports org.lisasp.alphatimer.heats.api.enums;
    exports org.lisasp.alphatimer.heats.domain;
    exports org.lisasp.alphatimer.heats.entity;
    exports org.lisasp.alphatimer.heats.service;

    opens org.lisasp.alphatimer.heats.api;
    opens org.lisasp.alphatimer.heats.api.enums;
    opens org.lisasp.alphatimer.heats.entity;

    requires org.lisasp.alphatimer.api.ares.serial;
    requires org.lisasp.alphatimer.api.refinedmessages;

    requires org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;
    requires org.lisasp.basics.spring.jpa;

    requires spring.context;
    requires spring.tx;
    requires spring.data.commons;

    requires java.persistence;
    requires java.validation;

    requires org.slf4j;

    requires static lombok;
}
