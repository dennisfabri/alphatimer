module org.lisasp.alphatimer.refinedmessages {
    exports org.lisasp.alphatimer.refinedmessages;

    requires transitive org.lisasp.alphatimer.api.refinedmessages;
    requires transitive org.lisasp.alphatimer.api.protocol;

    requires org.lisasp.basics.notification;

    requires static lombok;
}
