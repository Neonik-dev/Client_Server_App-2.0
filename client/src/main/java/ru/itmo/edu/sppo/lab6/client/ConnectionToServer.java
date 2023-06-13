package ru.itmo.edu.sppo.lab6.client;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.utils.ReadProperties;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class ConnectionToServer implements AutoCloseable {
    private static final String HOST_PROPERTIES = "server.host";
    private static final String PORT_PROPERTIES = "server.port";
    private Socket clientSocket;

    public Socket startConnection() throws IOException {
        log.debug("Подключаемся к серверу");

        String host = new ReadProperties().read(HOST_PROPERTIES);
        int port = Integer.parseInt(new ReadProperties().read(PORT_PROPERTIES));
        clientSocket = new Socket(host, port);

        log.debug("Подключение к серверу прошло успешно");
        return clientSocket;
    }

    @Override
    public void close() throws Exception {
        clientSocket.close();
    }
}
