package ru.itmo.edu.sppo.lab6;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.client.ConnectionToServer;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryInFileExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.CreateMusicBand;
import ru.itmo.edu.sppo.lab6.utils.MusicBandValidation;

import java.util.*;

@Slf4j
public class InputHandler {
    public static final String GET_ALL_COMMANDS = "getAllCommands";
    private static final String GREETING = "Добро пожаловать!\nМожете выполнить команду -> help, и узнаете все команды";
    private static final String HELP_TEXT =
            "Напишите любую команду из списка. Чтобы посмотреть список команд воспользуйтесь командой -> help";
    public static Scanner scanner;
    private final MusicBandValidation musicBandValidation;
    private Map<String, Boolean> SERVER_COMMANDS;

    {
        ClientRequest clientRequest = ClientRequest.builder().commandName(GET_ALL_COMMANDS).build();
        do {
            SERVER_COMMANDS = (Map<String, Boolean>) new ConnectionToServer().interactionWithServer(clientRequest);
            if (SERVER_COMMANDS == null) {
                try {
                    log.warn("Получить команды не удалось, попробуем еще раз через 5 секунд");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error("Невозможно получить команды от сервера");
                }
            }
        } while (SERVER_COMMANDS == null);
        log.debug("Команды сервера получены");

        musicBandValidation = new MusicBandValidation(SERVER_COMMANDS.keySet());
        scanner = new Scanner(System.in);
    }

    public void startInputHandler() {
        System.out.println(GREETING);
        while (scanner.hasNextLine()) {
            try {
                new ConnectionToServer().interactionWithServer(createBodyRequest());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.out.println(HELP_TEXT);
            } catch (UnexpectedCommandExceptions | IncorrectDataEntryInFileExceptions e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ClientRequest createBodyRequest() throws UnexpectedCommandExceptions, IncorrectDataEntryInFileExceptions {
        ClientRequest.ClientRequestBuilder clientRequest = ClientRequest.builder();

        String[] inputData = scanner.nextLine().split(" ");
        if (!SERVER_COMMANDS.containsKey(inputData[0])) {
            throw new NullPointerException();
        } else if (SERVER_COMMANDS.get(inputData[0])) {
            MusicBand musicBand = new CreateMusicBand(musicBandValidation).create(scanner);
            clientRequest.musicBand(musicBand);
        }

        return clientRequest
                .commandName(inputData[0])
                .argument(Arrays.copyOfRange(inputData, 1, inputData.length))
                .build();
    }
}
