package ru.itmo.edu.sppo.lab6.server;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.InputHandler;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class AcceptConnection {
    private final ServerSocketChannel serverSocketChannel;

    public AcceptConnection(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public void acceptConnection() throws IOException {
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.selectNow();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            if (!iterator.hasNext()) {
                continue;
            }

            SelectionKey selectionKey = iterator.next();
            iterator.remove();
            if (selectionKey.isAcceptable() && selectionKey.isValid()) {
                log.debug("К серверу подключился клиент");
                SocketChannel client = serverSocketChannel.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            }

            if (selectionKey.isReadable() && selectionKey.isWritable()) {
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ClientRequest request = new GetClientDTO(socketChannel).getDTO();

                SendClientDTO.sendDTO(
                        socketChannel,
                        InputHandler.executeCommand(request)
                );
                socketChannel.close();
            }
        }
    }
}
