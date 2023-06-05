package ru.itmo.edu.sppo.lab6.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class SendToServer implements AutoCloseable {
    private ObjectOutputStream output;

    public void send(Object request, Socket clientSocket) throws IOException {
        log.debug("Клиент отправляет сообщение на сервер");
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        output.writeObject(request);
    }

    public void close() throws IOException {
        output.close();
    }
}
