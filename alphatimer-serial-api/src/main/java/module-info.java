module org.lisasp.alphatimer.api.serial {
    exports org.lisasp.alphatimer.api.serial;
    exports org.lisasp.alphatimer.api.serial.configuration;
    exports org.lisasp.alphatimer.api.serial.exceptions;

    requires org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;

    requires static lombok;
}
