module org.lisasp.alphatimer.messagesstorage {
    exports org.lisasp.alphatimer.messagesstorage;

    requires org.lisasp.alphatimer.spring.jpa;
    requires org.lisasp.alphatimer.api.protocol;

    requires spring.context;

    requires static lombok;
}
