module org.lisasp.alphatimer.messagesstorage {
    exports org.lisasp.alphatimer.messagesstorage;
    opens org.lisasp.alphatimer.messagesstorage;

    requires org.lisasp.alphatimer.api.ares.serial;

    requires transitive org.lisasp.basics.spring.jpa;

    requires spring.context;
    requires spring.data.jpa;
    requires java.validation;
    requires java.persistence;

    requires static lombok;
}
