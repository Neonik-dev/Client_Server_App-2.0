package ru.itmo.edu.sppo.lab6.server;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class SendClientDTO {
    private SendClientDTO() {
    }

    public static void sendDTO(SocketChannel socketChannel, Object response) throws IOException {
        log.debug("Отправляем пользователю ответ");
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
        objectOutput.writeObject(response);

        ByteBuffer buffer = ByteBuffer.wrap(byteOutput.toByteArray());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }

        byteOutput.close();
        objectOutput.close();
    }
}
