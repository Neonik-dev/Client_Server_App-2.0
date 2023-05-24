package ru.itmo.edu.sppo.lab6.client;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.utils.ReadProperties;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class ConnectionToServer {
    private static final String ERROR_MESSAGE = "Что-то пошло не так, попробуйте снова";
    private static final String HOST_PROPERTIES = "server.host";
    private static final String PORT_PROPERTIES = "server.port";
    private Socket clientSocket;

    public void startConnection() throws IOException {
        log.debug("Подключаемся к серверу");

        String host = new ReadProperties().read(HOST_PROPERTIES);
        int port = Integer.parseInt(new ReadProperties().read(PORT_PROPERTIES));
        clientSocket = new Socket(host, port);

        log.debug("Подключение к серверу прошло успешно");
    }

    public Object interactionWithServer(Object request) {
        Object response = null;
        SendToServer sender = new SendToServer();
        try {
            startConnection();
            sender.send(request, clientSocket);
            response = GetFromServer.get(clientSocket);

            sender.stopOutputConnection();
            stopConnection();
        } catch (IOException e) {
            log.error("Ошибка при отправке на сервер: " + e.getMessage());
            System.out.println(ERROR_MESSAGE);
        }
        return response;
    }

    public void stopConnection() throws IOException {
        clientSocket.close();
    }
}
