module org.lisasp.alphatimer.protocol {
    exports org.lisasp.alphatimer.protocol;
    exports org.lisasp.alphatimer.protocol.exceptions;
    exports org.lisasp.alphatimer.protocol.parser to org.lisasp.alphatimer.test.protocol;
    exports org.lisasp.alphatimer.protocol.utils to org.lisasp.alphatimer.test.protocol;

    requires transitive org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;
    requires transitive org.lisasp.alphatimer.api.protocol;

    requires static lombok;
}
