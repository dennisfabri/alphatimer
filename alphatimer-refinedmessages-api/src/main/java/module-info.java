module org.lisasp.alphatimer.api.refinedmessages {
    exports org.lisasp.alphatimer.api.refinedmessages;
    exports org.lisasp.alphatimer.api.refinedmessages.dropped;
    exports org.lisasp.alphatimer.api.refinedmessages.accepted;
    exports org.lisasp.alphatimer.api.refinedmessages.accepted.enums;

    requires org.lisasp.alphatimer.api.protocol;

    requires java.base;

    requires static lombok;
}
