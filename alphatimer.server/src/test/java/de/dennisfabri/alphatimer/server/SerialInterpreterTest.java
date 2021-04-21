package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.messagesstorage.Messages;
import de.dennisfabri.alphatimer.serial.ByteListener;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import de.dennisfabri.alphatimer.serial.SerialPortReader;
import de.dennisfabri.alphatimer.serial.SerialPortWriter;
import de.dennisfabri.alphatimer.serial.configuration.SerialConfiguration;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class SerialInterpreterTest {

    private SerialInterpreter serialInterpreter;
    private Messages messages;
    private TestSerialPortReader serialPortReader;

    @BeforeEach
    void prepare() {
        ConfigurationValues config = new ConfigurationValues();
        config.setSerialPort("TestPort");
        config.setSerialConfiguration("ARES21");
        config.setStoragePath("target/test-SerialInterpreterTest");

        messages = Mockito.mock(Messages.class);

        serialPortReader = new TestSerialPortReader();

        serialInterpreter = new SerialInterpreter(messages,
                                                  new TestSerialConnectionBuilder(),
                                                  config);
    }

    @Test
    void getLegacyData() {
        String result = serialInterpreter.getLegacyData();
        assertEquals("<AlphaServer.Heat-array/>", result);
    }

    @Test
    void start() throws Exception {
        serialInterpreter.start();

        Mockito.verify(messages, times(0)).put(any());
    }

    @Test
    void startAndSend() throws Exception {
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
    void onDestroy() throws Exception {
        serialInterpreter.start();
        serialInterpreter.onDestroy();

        Mockito.verify(messages, times(0)).put(any());
    }

    private class TestSerialPortReader implements SerialPortReader {

        @Setter
        private ByteListener listener;

        void put(byte b) throws IOException {
            listener.notify(b);
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
        public SerialPortReader buildReader(ByteListener listener) {
            serialPortReader.setListener(listener);
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
