package ru.itmo.edu.sppo.lab6.client;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

@Slf4j
public class GetFromServer {
    public static Object get(Socket clientSocket) throws IOException {
        log.debug("Обрабатываем ответ от сервера");
        ClientResponse response = null;

        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {
            response = (ClientResponse) input.readObject();
        } catch (ClassNotFoundException e) {
            log.error("От сервера пришел неизвестный объект");
        }

        log.debug("Клиент получил от сервера следующее: ");
        System.out.println(response.answer());
        return response;
    }
}
