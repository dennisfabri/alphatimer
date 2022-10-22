module org.lisasp.alphatimer.messagesstorage {
    exports org.lisasp.alphatimer.messagesstorage;
    opens org.lisasp.alphatimer.messagesstorage;

    requires org.lisasp.alphatimer.api.ares.serial;

    requires transitive org.lisasp.basics.spring.jpa;

    requires spring.context;
    requires spring.data.jpa;
    requires jakarta.validation;
    requires jakarta.persistence;

    requires static lombok;
}
