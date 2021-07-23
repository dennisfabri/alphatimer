package org.lisasp.alphatimer.server;

import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageRepository;
import org.lisasp.alphatimer.legacy.LegacyTimeStorage;
import org.lisasp.alphatimer.messaging.ByteListener;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.serial.SerialPortReader;
import org.lisasp.alphatimer.server.mq.Sender;
import org.lisasp.alphatimer.server.testdoubles.TestDateFacade;
import org.lisasp.alphatimer.server.testdoubles.TestFileFacade;
import org.lisasp.alphatimer.server.testdoubles.TestSerialConnectionBuilder;
import org.lisasp.alphatimer.storage.Storage;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

class SerialInterpreterTest {

    private SerialInterpreter serialInterpreter;
    private DataHandlingMessageRepository messages;
    private TestSerialPortReader serialPortReader;
    private ConfigurationValues config;

    private final static String expectedDate = "2021-04-17";

    @BeforeEach
    void prepare() {
        config = new ConfigurationValues(new TestDateFacade());
        config.setSerialPort("TestPort");
        config.setSerialConfiguration("ARES21");
        config.setStoragePath("target/test-SerialInterpreterTest");

        messages = Mockito.mock(DataHandlingMessageRepository.class);

        serialPortReader = new TestSerialPortReader();

        Storage storage = new Storage("test", new TestFileFacade(), new TestDateFacade());

        serialInterpreter = new SerialInterpreter(messages,
                                                  new TestSerialConnectionBuilder(serialPortReader),
                                                  config,
                                                  storage,
                                                  new LegacyTimeStorage(),
                                                  new InputCollector(),
                                                  new DataHandlingMessageRefiner(),
                                                  Mockito.mock(Sender.class)
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

        Mockito.verify(messages, times(0)).put(any());
    }

    @Test
    void startWithAutoconfigure() {
        config.setSerialPort("");

        serialInterpreter.start();

        Mockito.verify(messages, times(0)).put(any());
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

        Mockito.verify(messages, times(1)).put(any());
    }

    @Test
    void onDestroy() {
        serialInterpreter.start();
        serialInterpreter.onDestroy();

        Mockito.verify(messages, times(0)).put(any());
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

}
