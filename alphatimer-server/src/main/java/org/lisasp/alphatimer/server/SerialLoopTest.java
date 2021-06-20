package org.lisasp.alphatimer.server;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialLoopTester;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.lisasp.alphatimer.serial.exceptions.NotEnoughSerialPortsException;

import java.io.IOException;
import java.util.TooManyListenersException;

@Slf4j
public class SerialLoopTest {

    void run(SerialConnectionBuilder serialConnectionBuilder) {
        try {
            new SerialLoopTester(serialConnectionBuilder).testSerialConnection(SerialConfiguration.ARES21);
        } catch (NotEnoughSerialPortsException e) {
            log.error("Less than two serial ports found.");
        } catch (TooManyListenersException | UnsupportedCommOperationException | NoSuchPortException | PortInUseException e) {
            log.error("Problem initializing serial port", e);
        } catch (IOException e) {
            log.error("Problem communicating with serial port", e);
        } catch (InterruptedException e) {
            log.error("Problem during execution", e);
        }
    }
}
