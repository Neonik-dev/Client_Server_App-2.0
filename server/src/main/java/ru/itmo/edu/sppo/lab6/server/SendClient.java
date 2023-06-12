package ru.itmo.edu.sppo.lab6.server;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class SendClient extends Thread {
    private static final String NAME = "SendClientThread";
    private final SocketChannel socketChannel;
    private final Object response;

    public SendClient(SocketChannel socketChannel, Object response) {
        super(NAME);
        this.socketChannel = socketChannel;
        this.response = response;
    }

    @Override
    public void run() {
        log.debug("Отправляем пользователю ответ");
        try (ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
             ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput)
        ) {
            objectOutput.writeObject(response);
            ByteBuffer buffer = ByteBuffer.wrap(byteOutput.toByteArray());
            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
            socketChannel.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            log.error("Отправить сообщение не удалось!");
        }
    }
}
