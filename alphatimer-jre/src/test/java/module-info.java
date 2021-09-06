module org.lisasp.alphatimer.test.jre {
    requires org.lisasp.alphatimer.jre;

    opens org.lisasp.alphatimer.test.jre.date;
    opens org.lisasp.alphatimer.test.jre.io;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
}
