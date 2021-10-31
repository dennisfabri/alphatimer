module org.lisasp.alphatimer.ares.serial {
    exports org.lisasp.alphatimer.ares.serial;
    exports org.lisasp.alphatimer.ares.serial.exceptions;
    exports org.lisasp.alphatimer.ares.serial.parser to org.lisasp.alphatimer.test.ares.serial;
    exports org.lisasp.alphatimer.ares.serial.utils to org.lisasp.alphatimer.test.ares.serial;

    requires transitive org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;
    requires transitive org.lisasp.alphatimer.api.ares.serial;

    requires static lombok;
}
