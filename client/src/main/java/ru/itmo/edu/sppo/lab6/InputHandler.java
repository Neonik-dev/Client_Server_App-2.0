package ru.itmo.edu.sppo.lab6;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.client.ConnectionToServer;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.ExitCommandExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryInFileExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.CreateMusicBand;
import ru.itmo.edu.sppo.lab6.utils.ReadProperties;
import ru.itmo.edu.sppo.lab6.utils.ValidationMusicBand;

import java.util.*;

@Slf4j
public class InputHandler {
    private static final String TIMEOUT_PROPERTIES = "server.timeout";
    private static final String GET_ALL_COMMANDS = "getAllCommands";
    private static final String EXIT_COMMAND = "exit";
    private static final String EXIT_TEXT = "Клиент завершает свою работу. До связи\uD83E\uDD19!";
    private static final String GREETING = "Добро пожаловать!\nМожете выполнить команду -> help, и узнаете все команды";
    private static final String HELP_TEXT =
            "Напишите любую команду из списка. Чтобы посмотреть список команд воспользуйтесь командой -> help";
    private static final int TIMEOUT;
    private static final Scanner SCANNER;
    private static final ValidationMusicBand VALIDATION_MUSIC_BAND;
    private static Map<String, Map<String, Boolean>> serverCommands;

    static {
        TIMEOUT = Integer.parseInt(new ReadProperties().read(TIMEOUT_PROPERTIES));

        ClientRequest clientRequest = ClientRequest.builder().commandName(GET_ALL_COMMANDS).build();
        do {
            serverCommands = (Map<String, Map<String, Boolean>>) new ConnectionToServer()
                    .interactionWithServer(clientRequest);
            if (serverCommands == null) {
                waitStartServer();
            }
        } while (serverCommands == null);
        log.debug("Команды сервера получены");

        VALIDATION_MUSIC_BAND = new ValidationMusicBand(serverCommands.keySet());
        SCANNER = new Scanner(System.in);
    }

    private static void waitStartServer() {
        try {
            log.warn("Получить команды не удалось, попробуем еще раз через 5 секунд");
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            log.error("Невозможно получить команды от сервера");
        }
    }

    public void startInputHandler() {
        System.out.println(GREETING);
        while (SCANNER.hasNextLine()) {
            try {
                new ConnectionToServer().interactionWithServer(createBodyRequest());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.out.println(HELP_TEXT);
            } catch (UnexpectedCommandExceptions | IncorrectDataEntryInFileExceptions
                     | IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            } catch (ExitCommandExceptions e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private ClientRequest createBodyRequest() throws UnexpectedCommandExceptions,
            IncorrectDataEntryInFileExceptions, IncorrectDataEntryExceptions, ExitCommandExceptions {
        ClientRequest.ClientRequestBuilder clientRequest = ClientRequest.builder();

        String[] inputData = SCANNER.nextLine().split(" ");
        String commandName = inputData[0];

        if (commandName.equals(EXIT_COMMAND)) {
            throw new ExitCommandExceptions(EXIT_TEXT);
        } else if (!serverCommands.containsKey(commandName)) {
            throw new NullPointerException();
        }

        if (serverCommands.get(commandName).get("firstGetCommand")) {
            postServerAdditionalRequest(inputData);
        }
        if (serverCommands.get(commandName).get("transmitObject")) {
            MusicBand musicBand = new CreateMusicBand(VALIDATION_MUSIC_BAND).create(SCANNER);
            clientRequest.musicBand(musicBand);
        }

        return clientRequest
                .commandName(commandName)
                .argument(Arrays.copyOfRange(inputData, 1, inputData.length))
                .build();
    }

    public void postServerAdditionalRequest(String[] inputData) throws IncorrectDataEntryExceptions {
        ClientResponse additionalCheckArgs = (ClientResponse) new ConnectionToServer()
                .interactionWithServer(ClientRequest.builder()
                        .commandName(inputData[0])
                        .argument(Arrays.copyOfRange(inputData, 1, inputData.length))
                        .build()
                );
        if (additionalCheckArgs.answer() != null && additionalCheckArgs.answer().length() != 0) {
            throw new IncorrectDataEntryExceptions(additionalCheckArgs.answer());
        }
    }
}
