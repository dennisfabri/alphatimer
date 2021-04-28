package org.lisasp.alphatimer.server;

import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageRepository;
import org.lisasp.alphatimer.legacy.LegacyTimeStorage;
import org.lisasp.alphatimer.messaging.ByteListener;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialPortReader;
import org.lisasp.alphatimer.serial.SerialPortWriter;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.storage.FileFacade;
import org.lisasp.alphatimer.storage.Storage;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

class SerialInterpreterTest {

    private SerialInterpreter serialInterpreter;
    private DataHandlingMessageRepository messages;
    private TestSerialPortReader serialPortReader;
    private ConfigurationValues config;

    private final static String expectedDate = "2020-01-05";

    @BeforeEach
    void prepare() {
        config = new ConfigurationValues();
        config.setSerialPort("TestPort");
        config.setSerialConfiguration("ARES21");
        config.setStoragePath("target/test-SerialInterpreterTest");

        messages = Mockito.mock(DataHandlingMessageRepository.class);

        serialPortReader = new TestSerialPortReader();

        Storage storage = new Storage("test", new FileFacade() {
            @Override
            public void write(String filename, byte b) {
            }

            @Override
            public byte[] read(String filename) {
                return new byte[0];
            }

            @Override
            public String getSeparator() {
                return "/";
            }
        }, () -> LocalDate.of(2021, Month.APRIL, 20));

        serialInterpreter = new SerialInterpreter(messages,
                                                  new TestSerialConnectionBuilder(),
                                                  config,
                                                  storage,
                                                  new LegacyTimeStorage(),
                                                  new InputCollector(),
                                                  () -> LocalDate.of(2020, Month.JANUARY, 5)
        );
    }

    @Test
    void getLegacyData() {
        String result = serialInterpreter.getLegacyData();
        assertEquals("<AlphaServer.Heat-array/>", result);
    }

    @Test
    void start() {
        serialInterpreter.start();

        Mockito.verify(messages, times(0)).put(any(), eq(expectedDate));
    }

    @Test
    void startWithAutoconfigure() {
        config.setSerialPort("");

        serialInterpreter.start();

        Mockito.verify(messages, times(0)).put(any(), eq(expectedDate));
    }

    @Test
    void startAndSend() {
        serialInterpreter.start();

        for (byte b : DataHandlingMessageTestData.message1) {
            serialPortReader.put(b);
        }
        for (byte b : DataHandlingMessageTestData.message2) {
            serialPortReader.put(b);
        }

        Mockito.verify(messages, times(1)).put(any(), eq(expectedDate));
    }

    @Test
    void onDestroy() {
        serialInterpreter.start();
        serialInterpreter.onDestroy();

        Mockito.verify(messages, times(0)).put(any(), eq(expectedDate));
    }

    private static class TestSerialPortReader implements SerialPortReader {

        @Setter
        private ByteListener listener;

        void put(byte b) {
            listener.accept(b);
        }

        @Override
        public SerialPortReader register(ByteListener listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public void close() {
            // Nothing to do
        }
    }

    private class TestSerialConnectionBuilder implements SerialConnectionBuilder {
        @Override
        public SerialConnectionBuilder configure(String port,
                                                 SerialConfiguration config) {
            return this;
        }

        @Override
        public SerialPortReader buildReader() {
            return serialPortReader;
        }

        @Override
        public SerialPortWriter buildWriter() {
            return Mockito.mock(SerialPortWriter.class);
        }

        @Override
        public String[] listAvailablePorts() {
            return new String[]{"TestPort1", "TestPort2"};
        }

        @Override
        public String autoconfigurePort() {
            return "TestPort3";
        }
    }
}
