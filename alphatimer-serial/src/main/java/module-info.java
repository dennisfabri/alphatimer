module org.lisasp.alphatimer.serial {
    exports org.lisasp.alphatimer.serial;
    exports org.lisasp.alphatimer.serial.configuration;
    exports org.lisasp.alphatimer.serial.exceptions;

    requires org.lisasp.alphatimer.messaging;
    requires org.lisasp.alphatimer.jre;

    requires nrjavaserial;
    requires org.slf4j;
    requires java.base;

    requires static lombok;
}
