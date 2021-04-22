package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.serial.DefaultSerialConnectionBuilder;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.Locale;

class CommandLineInterpreter {

    private final SerialConnectionBuilder serialConnectionBuilder;

    CommandLineInterpreter() {
        this(new DefaultSerialConnectionBuilder());
    }

    CommandLineInterpreter(SerialConnectionBuilder serialConnectionBuilder) {
        this.serialConnectionBuilder = serialConnectionBuilder;
    }

    boolean run(String... args)
            throws UnsupportedCommOperationException, NoSuchPortException, PortInUseException, IOException {
        if (args.length > 0) {
            String command = getFromArgs(args, 0).trim().toLowerCase(Locale.ROOT);
            if (command.equals("-seriallooptest")) {
                new SerialLoopTest().run(serialConnectionBuilder);
                return true;
            }
            if (command.equals("-writetoserialport")) {
                new WriteToSerialPort().run(getFromArgs(args, 1),
                                            getFromArgs(args, 2),
                                            getFromArgs(args, 3),
                                            serialConnectionBuilder);
                return true;
            }
        }
        return false;
    }

    private static String getFromArgs(String[] args, int pos) {
        if (args.length > pos) {
            return args[pos];
        }
        return "";
    }
}
