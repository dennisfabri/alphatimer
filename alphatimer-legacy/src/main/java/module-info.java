module org.lisasp.alphatimer.legacy {
    exports org.lisasp.alphatimer.legacy;
    exports org.lisasp.alphatimer.legacy.entity;
    exports org.lisasp.alphatimer.legacy.dto;

    requires org.lisasp.alphatimer.api.protocol;

    requires org.lisasp.alphatimer.spring.jpa;

    requires spring.data.jpa;
    requires java.validation;

    requires kotlin.stdlib;

    requires static lombok;
}
