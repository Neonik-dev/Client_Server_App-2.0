package ru.itmo.edu.sppo.lab6.server;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.document.ReadProperties;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.utils.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

@Slf4j
public class GetClient extends Thread {
    private static final String NAME = "GetClientThread";
    private static final int COUNT_RETRIES = 10;
    private static final int BUFFER_SIZE = Integer.parseInt(ReadProperties.read("bufferSize"));
    private final SocketChannel socketChannel;
    private volatile ClientRequest response;

    public GetClient(SocketChannel socketChannel) {
        super(NAME);
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        log.debug("Началась обработка пришедшего сообщения");
        int retries = 0;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while (response == null && retries < COUNT_RETRIES) {
                Timeout.tcpTimeout();
                readTcpPackage(outputStream);

                try (ByteArrayInputStream input = new ByteArrayInputStream(outputStream.toByteArray());
                     ObjectInputStream ois = new ObjectInputStream(input)
                ) {
                    response = (ClientRequest) ois.readObject();
                    log.debug("Сервер получил от клиента следующее: " + response.toString());
                } catch (IOException e) {
                    log.warn("Еще не все пакеты клиента дошли до сервера");
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage());
                }
                retries++;
            }

            if (retries >= COUNT_RETRIES) {
                log.warn("С клиента пришли битые данные. Невозможно десерриализовать.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readTcpPackage(ByteArrayOutputStream outputStream) throws IOException {
        ByteBuffer bufferResponse = ByteBuffer.allocate(BUFFER_SIZE);
        byte[] lastChunk = null;

        while (socketChannel.read(bufferResponse) > 0) {
            if (lastChunk != null) {
                outputStream.write(lastChunk);
            }
            lastChunk = bufferResponse.array();
            bufferResponse = ByteBuffer.allocate(BUFFER_SIZE);
        }
        outputStream.write(clearEOFByteArray(lastChunk));
    }

    private byte[] clearEOFByteArray(byte[] array) {
        int cursor = array.length - 1;
        while (cursor >= 0 && array[cursor] == 0) {
            cursor--;
        }
        return Arrays.copyOfRange(array, 0, cursor + 1);
    }

    public ClientRequest getResponse() {
        return response;
    }
}
