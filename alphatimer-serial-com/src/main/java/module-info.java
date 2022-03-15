module org.lisasp.alphatimer.serial {
    exports org.lisasp.alphatimer.serial.com;

    requires org.lisasp.alphatimer.api.serial;
    requires org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;

    requires nrjavaserial;

    requires org.slf4j;

    requires static lombok;
}
