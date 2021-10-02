module org.lisasp.alphatimer.serial {
    exports org.lisasp.alphatimer.serial;
    exports org.lisasp.alphatimer.serial.configuration;
    exports org.lisasp.alphatimer.serial.exceptions;

    requires org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;

    requires nrjavaserial;

    requires static lombok;
}
