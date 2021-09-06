module org.lisasp.alphatimer.spring.jpa {
    exports org.lisasp.alphatimer.spring.jpa;

    requires transitive java.persistence;

    requires org.slf4j;
    requires transitive spring.data.commons;

    requires static lombok;
}
