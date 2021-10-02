module org.lisasp.alphatimer.test.serial {
    requires org.lisasp.alphatimer.serial;

    opens org.lisasp.alphatimer.test.serial;
    opens org.lisasp.alphatimer.test.serial.configuration;

    requires org.lisasp.basics.jre;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
}
