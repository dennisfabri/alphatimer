module org.lisasp.alphatimer.jre {
    exports org.lisasp.alphatimer.jre.io;
    exports org.lisasp.alphatimer.jre.date;

    requires java.base;

    requires static lombok;
}
