package ru.itmo.edu.sppo.lab6.utils;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.client.InteractionWithServer;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;

import java.util.Map;

@Slf4j
public class GetAllServerCommands {
    private static final String GET_ALL_COMMANDS = "getAllCommands";
    private static final String TIMEOUT_PROPERTIES = "server.timeout";
    private static final int TIMEOUT;
    private static Map<String, Map<String, Boolean>> serverCommands;

    private GetAllServerCommands() {
    }

    static {
        TIMEOUT = Integer.parseInt(new ReadProperties().read(TIMEOUT_PROPERTIES));
        ClientRequest clientRequest = ClientRequest.builder().commandName(GET_ALL_COMMANDS).build();

        do {
            serverCommands = (Map<String, Map<String, Boolean>>) new InteractionWithServer()
                    .interaction(clientRequest);
            if (serverCommands == null) {
                waitStartServer();
            }
        } while (serverCommands == null);
        log.debug("Команды сервера получены");
    }

    private static void waitStartServer() {
        try {
            log.warn("Получить команды не удалось, попробуем еще раз через 5 секунд");
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            log.error("Невозможно получить команды от сервера");
        }
    }

    public static Map<String, Map<String, Boolean>> getCommands() {
        return serverCommands;
    }
}
