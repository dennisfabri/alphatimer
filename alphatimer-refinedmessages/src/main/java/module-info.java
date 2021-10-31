module org.lisasp.alphatimer.refinedmessages {
    exports org.lisasp.alphatimer.refinedmessages;

    requires transitive org.lisasp.alphatimer.api.ares.serial;
    requires transitive org.lisasp.alphatimer.api.refinedmessages;

    requires org.lisasp.basics.notification;

    requires static lombok;
}
