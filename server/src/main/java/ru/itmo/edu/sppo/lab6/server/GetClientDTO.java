package ru.itmo.edu.sppo.lab6.server;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.utils.ReadProperties;
import ru.itmo.edu.sppo.lab6.utils.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

@Slf4j
public class GetClientDTO {
    private static final String BUFFER_SIZE_PROPERTIES = "buffer_size";
    private final int BUFFER_SIZE;
    private final SocketChannel socketChannel;

    public GetClientDTO(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        BUFFER_SIZE = Integer.parseInt(
                new ReadProperties().read(BUFFER_SIZE_PROPERTIES)
        );
    }

    public ClientRequest getDTO() throws IOException {
        log.debug("Идет обработка пришедшего сообщения");
        ClientRequest response = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while (response == null) {
            Timeout.tcpTimeout();
            readTcpPackage(outputStream);

            try (
                    ByteArrayInputStream input = new ByteArrayInputStream(outputStream.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(input)
            ) {
                response = (ClientRequest) ois.readObject();
                log.debug("Сервер получил от клиента следующее: " + response);
            } catch (IOException e) {
                log.warn("Еще не все пакеты клиента дошли до сервера");
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage());
            }
        }
        outputStream.close();
        return response;
    }

    private void readTcpPackage(ByteArrayOutputStream outputStream) throws IOException {
        ByteBuffer bufferResponse = ByteBuffer.allocate(BUFFER_SIZE);

        while (socketChannel.read(bufferResponse) > 0) {
            outputStream.write(
                    clearByteArray(bufferResponse.array())
            );
            bufferResponse = ByteBuffer.allocate(BUFFER_SIZE);
        }
    }

    private byte[] clearByteArray(byte[] array) {
        int cursor = array.length - 1;
        while (cursor >= 0 && array[cursor] == 0) {
            cursor--;
        }
        return Arrays.copyOfRange(array, 0, cursor + 1);
    }
}
