module org.lisasp.alphatimer.serial.tcp {
    exports org.lisasp.alphatimer.serial.tcp;

    requires org.lisasp.alphatimer.api.serial;

    requires org.lisasp.basics.jre;
    requires org.lisasp.basics.notification;

    requires org.slf4j;

    requires static lombok;
}
