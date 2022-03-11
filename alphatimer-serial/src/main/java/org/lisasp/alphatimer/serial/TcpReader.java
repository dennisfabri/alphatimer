package org.lisasp.alphatimer.serial;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.basics.notification.primitive.ByteListener;
import org.lisasp.basics.notification.primitive.ByteNotifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

@Slf4j
public class TcpReader implements SerialPortReader {

    private final ByteNotifier notifier = new ByteNotifier();

    private final String server;
    private final int port;

    private Socket socket;
    private InputStream input;

    private final Thread reader;

    public TcpReader(String server, int port) {
        log.info("Starting reader at tcp://{}:{}", server, port);

        this.server = server;
        this.port = port;

        reader = new Thread(() -> read());
        reader.setDaemon(true);
        reader.start();
    }

    private void read() {
        while (!Thread.interrupted()) {
            try {
                checkConnection();
                manageConnection();
                readOnce();
            } catch (SocketException ex) {
                log.warn("Socket problem", ex);
                closeConnection();
                sleep(30);
            } catch (IOException ex) {
                log.warn("Exception while reading serial tcp input", ex);
                closeConnection();
                sleep(1);
            }
        }
    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    private void checkConnection() {
        if (socket == null) {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
            input = null;
            return;
        }
        if (socket.isClosed()) {
            socket = null;
            input = null;
        }
    }

    @SneakyThrows
    private void sleep(int seconds) {
        Thread.sleep(seconds * 1000);
    }

    private void manageConnection() {
        try {
            if (socket == null) {
                socket = new Socket(server, port);
                input = socket.getInputStream();
            }
        } catch (IOException ex) {
            log.warn("Could not connect to serial tcp server", ex);
            closeConnection();
        }
    }

    private void readOnce() throws IOException {
        byte[] data = new byte[1024];
        int amount = input.read(data);
        for (int x = 0; x < amount; x++) {
            notifier.accept(data[x]);
        }
    }

    public SerialPortReader register(ByteListener listener) {
        notifier.register(listener);
        return this;
    }

    @Override
    public void close() {
        reader.interrupt();
        try {
            input.close();
        } catch (IOException e) {
            log.warn("Exception on socket input close.", e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            log.warn("Exception on socket close.", e);
        }
    }
}
