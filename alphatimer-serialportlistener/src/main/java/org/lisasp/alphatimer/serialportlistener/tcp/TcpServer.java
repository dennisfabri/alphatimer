package org.lisasp.alphatimer.serialportlistener.tcp;

import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class TcpServer {

    private final ServerSocket serverSocket;
    private final Thread waiter;

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    private Socket socket;

    public TcpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        waiter = new Thread(() -> serve());
        waiter.start();
    }

    @SneakyThrows
    private void serve() {
        while (true) {
                socket = serverSocket.accept();
                serveOnce();
                while (isConnectionOpen()) {
                    Thread.sleep(100);
                }
        }
    }

    private boolean isConnectionOpen() {
        try {
            return socket != null && !socket.isClosed();
        } catch (RuntimeException re) {
            return false;
        }
    }

    @Synchronized("stream")
    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            log.warn("Exception during close.", e);
        } finally {
            socket = null;
        }
    }

    @Synchronized("stream")
    private void serveOnce() {
        try {
            if (stream.size() == 0) {
                return;
            }
            socket.getOutputStream().write(stream.toByteArray());
            stream.reset();
        } catch (IOException e) {
            log.warn("Exception during serveOnce.", e);
            close();
        }
    }

    @Synchronized("stream")
    public void send(byte data) {
        stream.write(data);
        serveOnce();
    }
}
