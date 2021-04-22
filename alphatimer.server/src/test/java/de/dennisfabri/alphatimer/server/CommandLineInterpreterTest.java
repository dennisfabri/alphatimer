package de.dennisfabri.alphatimer.server;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import de.dennisfabri.alphatimer.serial.SerialPortReader;
import de.dennisfabri.alphatimer.serial.SerialPortWriter;
import de.dennisfabri.alphatimer.storage.ActualFile;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommandLineInterpreterTest {

    private CommandLineInterpreter cli;

    @BeforeEach
    void prepare()
            throws
            TooManyListenersException,
            UnsupportedCommOperationException,
            NoSuchPortException,
            PortInUseException {
        SerialConnectionBuilder serialConnectionBuilder = Mockito.mock(SerialConnectionBuilder.class);
        when(serialConnectionBuilder.listAvailablePorts()).thenReturn(new String[]{"Port1", "Port2"});
        when(serialConnectionBuilder.autoconfigurePort()).thenReturn("AutoconfiguredPort");
        when(serialConnectionBuilder.configure(any(), any())).thenReturn(serialConnectionBuilder);
        when(serialConnectionBuilder.buildReader(any())).thenReturn(Mockito.mock(SerialPortReader.class));
        when(serialConnectionBuilder.buildWriter()).thenReturn(Mockito.mock(SerialPortWriter.class));
        cli = new CommandLineInterpreter(serialConnectionBuilder);
    }

    @AfterEach
    void cleanup() throws IOException {
        cli = null;
        Files.deleteIfExists(Path.of("target/test-data/demo.dat"));
    }

    @Test
    void ensureDefaultConstructor() {
        new CommandLineInterpreter();
    }

    @Test
    void serialLoopTest() throws Exception {
        boolean result = cli.run("-seriallooptest");
        assertTrue(result);
    }

    @Test
    void writeToSerialPort() throws Exception {
        ListAppender<ILoggingEvent> appender = prepareLogger(WriteToSerialPort.class);

        new ActualFile().write("target/test-data/demo.dat", (byte) 0);
        boolean result = cli.run("-writetoserialport", "target/test-data/demo.dat", "TestPort1", "ARES21");

        assertTrue(result);

        List<String> messages = appender.list.stream().map(e -> e.getMessage()).collect(Collectors.toList());
        assertTrue(2 <= messages.size());
        assertTrue(messages.contains("Writing content of file {} to port {} with settings {}."));
        assertEquals("Finished", messages.get(messages.size() - 1));
    }

    @Test
    void writeToSerialPortAutoconfigure() throws Exception {
        ListAppender<ILoggingEvent> appender = prepareLogger(WriteToSerialPort.class);

        new ActualFile().write("target/test-data/demo.dat", (byte) 0);
        boolean result = cli.run("-writetoserialport", "target/test-data/demo.dat", "", "ARES21");

        assertTrue(result);

        List<String> messages = appender.list.stream().map(e -> e.getMessage()).collect(Collectors.toList());
        assertTrue(2 <= messages.size());
        assertTrue(messages.contains("Writing content of file {} to port {} with settings {}."));
        assertEquals("Finished", messages.get(messages.size() - 1));
    }

    @Test
    void writeToSerialPortWithoutFilename() throws Exception {
        ListAppender<ILoggingEvent> appender = prepareLogger(WriteToSerialPort.class);

        new ActualFile().write("target/test-data/demo.dat", (byte) 0);
        boolean result = cli.run("-writetoserialport", "", "TestPort", "ARES21");

        assertTrue(result);

        List<String> messages = appender.list.stream().map(e -> e.getMessage()).collect(Collectors.toList());
        assertTrue(1 <= messages.size());
        assertEquals("Filename not specified.", messages.get(messages.size() - 1));
    }

    @Test
    void emptyTest() throws Exception {
        boolean result = cli.run("");
        assertFalse(result);
    }

    private ListAppender<ILoggingEvent> prepareLogger(Class<?> loggedClass) {
        Logger logger = LoggerFactory.getLogger(loggedClass);
        if (!(logger instanceof ch.qos.logback.classic.Logger)) {
            throw new RuntimeException(String.format("Logger should be an instance of %s but is %s",
                                                     ch.qos.logback.classic.Logger.class,
                                                     logger.getClass()));
        }

        ch.qos.logback.classic.Logger classicLogger = (ch.qos.logback.classic.Logger) logger;
        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        classicLogger.addAppender(listAppender);
        return listAppender;
    }
}
