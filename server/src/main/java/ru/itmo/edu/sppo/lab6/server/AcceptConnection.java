package ru.itmo.edu.sppo.lab6.server;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.InputHandler;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@Slf4j
public class AcceptConnection {
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private Thread senderThread = null;
    private SocketChannel socketChannel = null;

    public AcceptConnection(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void acceptConnection() throws IOException {
        Optional<SelectionKey> optionalSelectionKey;
        SelectionKey selectionKey;

        while (true) {
            optionalSelectionKey = waitingMessageFromClient();
            if (optionalSelectionKey.isEmpty()) {
                continue;
            }
            selectionKey = optionalSelectionKey.get();

            if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                log.debug("К серверу подключился клиент");
                SocketChannel client = serverSocketChannel.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            } else if (selectionKey.isValid() && selectionKey.isReadable() && selectionKey.isWritable()) {
                socketChannel = (SocketChannel) selectionKey.channel();
                getAndSendClient();
            }
        }
    }

    private void getAndSendClient() {
        try {
            GetClient getClientThread = new GetClient(socketChannel);
            getClientThread.start();
            getClientThread.join();
            ClientRequest request = getClientThread.getResponse();

            senderThread = new SendClient(socketChannel, InputHandler.executeCommand(request));
            senderThread.start();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private Optional<SelectionKey> waitingMessageFromClient() throws IOException {
        selector.selectNow();
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        if (!iterator.hasNext()) {
            return Optional.empty();
        }
        closeOldSocketChannel();

        SelectionKey selectionKey = iterator.next();
        iterator.remove();
        return Optional.of(selectionKey);
    }

    private void closeOldSocketChannel() throws IOException {
        if (senderThread != null && socketChannel.isConnected() && senderThread.isAlive()) {
            try {
                senderThread.join();
            } catch (InterruptedException e) {
                log.debug(senderThread.getName());
            }
        } else if (socketChannel != null && socketChannel.isConnected()) {
            socketChannel.close();
        }
    }
}
