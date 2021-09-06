module org.lisasp.alphatimer.datatests {
    exports org.lisasp.alphatimer.datatests;

    requires transitive org.lisasp.alphatimer.api.protocol;
    requires transitive org.lisasp.alphatimer.legacy;

    requires java.base;

    requires static lombok;
}
