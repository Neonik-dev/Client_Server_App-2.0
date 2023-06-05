package ru.itmo.edu.sppo.lab6.client;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class InteractionWithServer {
    private static final String ERROR_MESSAGE = "Что-то пошло не так, попробуйте снова";

    public Object interaction(Object request) {
        Object response = null;
        try (ConnectionToServer connection = new ConnectionToServer();
             SendToServer sender = new SendToServer()
        ) {
            Socket clientSocket = connection.startConnection();
            sender.send(request, clientSocket);
            response = GetFromServer.get(clientSocket);
        } catch (Exception e) {
            log.error("Ошибка при отправке на сервер: " + e.getMessage());
            System.out.println(ERROR_MESSAGE);
        }
        return response;
    }
}
