module org.lisasp.alphatimer.legacy {
    exports org.lisasp.alphatimer.legacy;
    exports org.lisasp.alphatimer.legacy.entity;
    exports org.lisasp.alphatimer.legacy.dto;

    opens org.lisasp.alphatimer.legacy.dto;
    opens org.lisasp.alphatimer.legacy.entity;

    requires org.lisasp.alphatimer.api.ares.serial;

    requires transitive org.lisasp.basics.spring.jpa;

    requires java.validation;

    requires static lombok;
}
