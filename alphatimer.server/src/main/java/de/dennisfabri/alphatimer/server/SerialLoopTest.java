package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import de.dennisfabri.alphatimer.serial.SerialLoopTester;
import de.dennisfabri.alphatimer.serial.exceptions.NotEnoughSerialPortsException;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.TooManyListenersException;

@Slf4j
public class SerialLoopTest {

    void run(SerialConnectionBuilder serialConnectionBuilder) {
        try {
            new SerialLoopTester(serialConnectionBuilder).test();
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
