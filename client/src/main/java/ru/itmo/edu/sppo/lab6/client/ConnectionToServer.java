package ru.itmo.edu.sppo.lab6.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

@Slf4j
public class ConnectionToServer {
    private static final String ERROR_MESSAGE = "Что-то пошло не так, попробуйте снова";
    private static final String NAME_PROPERTIES = "client.properties";
    private static final String HOST_PROPERTIES = "server.host";
    private static final String PORT_PROPERTIES = "server.port";
    private Socket clientSocket;
    private String host;
    private int port;

    public void startConnection() throws IOException {
        log.debug("Подключаемся к серверу");
        getServerURI();
        clientSocket = new Socket(host, port);
        log.debug("Подключение к серверу прошло успешно");
    }

    private void getServerURI() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(NAME_PROPERTIES);
        Properties properties = new Properties();
        properties.load(inputStream);

        host = properties.getProperty(HOST_PROPERTIES);
        port = Integer.parseInt(properties.getProperty(PORT_PROPERTIES));

        if (inputStream != null) {
            inputStream.close();
        }
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
