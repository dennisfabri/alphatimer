package org.lisasp.alphatimer.server.testdoubles;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialPortReader;
import org.lisasp.alphatimer.serial.SerialPortWriter;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.mockito.Mockito;

@RequiredArgsConstructor
public class TestSerialConnectionBuilder implements SerialConnectionBuilder {

    private final SerialPortReader serialPortReader;

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
