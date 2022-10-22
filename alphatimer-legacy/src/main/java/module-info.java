module org.lisasp.alphatimer.legacy {
    exports org.lisasp.alphatimer.legacy;
    exports org.lisasp.alphatimer.legacy.entity;
    exports org.lisasp.alphatimer.legacy.dto;

    opens org.lisasp.alphatimer.legacy.dto;
    opens org.lisasp.alphatimer.legacy.entity;

    requires org.lisasp.alphatimer.api.ares.serial;

    requires transitive org.lisasp.basics.spring.jpa;

    requires jakarta.validation;
    requires jakarta.persistence;

    requires org.slf4j;

    requires static lombok;
}
