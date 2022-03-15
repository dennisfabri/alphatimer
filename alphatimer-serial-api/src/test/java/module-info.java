module org.lisasp.alphatimer.test.api.serial {
    requires org.lisasp.alphatimer.api.serial;

    opens org.lisasp.alphatimer.test.api.serial;
    opens org.lisasp.alphatimer.test.api.serial.configuration;

    requires org.lisasp.basics.jre;

    requires org.mockito;
    requires net.bytebuddy;
    requires net.bytebuddy.agent;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
}
