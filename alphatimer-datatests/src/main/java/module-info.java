module org.lisasp.alphatimer.datatests {
    exports org.lisasp.alphatimer.datatests;
    exports org.lisasp.alphatimer.datatests.resources;

    opens org.lisasp.alphatimer.datatests;
    opens org.lisasp.alphatimer.datatests.resources;

    requires org.slf4j;

    requires transitive org.lisasp.alphatimer.legacy;

    requires static lombok;
}
