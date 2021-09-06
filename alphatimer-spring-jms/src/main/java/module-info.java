module org.lisasp.alphatimer.spring.jms {
    exports org.lisasp.alphatimer.spring.jms;

    requires org.slf4j;
    requires annotations;
    requires spring.jms;
    requires spring.context;
    requires com.fasterxml.jackson.databind;
    requires jakarta.jms.api;

    requires static lombok;
}
