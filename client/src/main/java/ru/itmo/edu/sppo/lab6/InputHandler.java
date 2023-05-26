package ru.itmo.edu.sppo.lab6;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.client.ConnectionToServer;
import ru.itmo.edu.sppo.lab6.command.BaseCommand;
import ru.itmo.edu.sppo.lab6.command.Commands;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.*;
import ru.itmo.edu.sppo.lab6.utils.CreateMusicBand;
import ru.itmo.edu.sppo.lab6.utils.GetAllServerCommands;
import ru.itmo.edu.sppo.lab6.utils.ValidationMusicBand;

import java.util.*;

@Slf4j
public class InputHandler {
    @Getter
    @Setter
    private static Scanner scanner;
    private static final String CLOSE_TEXT = "Сканнер закрылся. Завершение работы";
    private static final String GREETING = "Добро пожаловать!\nМожете выполнить команду -> help, и узнаете все команды";
    private static final String HELP_TEXT =
            "Напишите любую команду из списка. Чтобы посмотреть список команд воспользуйтесь командой -> help";
    private static final ValidationMusicBand VALIDATION_MUSIC_BAND;
    private static final Map<String, BaseCommand> CLIENT_COMMAND = new Commands().getAllCommand();
    private static final Map<String, Map<String, Boolean>> SERVER_COMMANDS;

    static {
        SERVER_COMMANDS = GetAllServerCommands.getCommands();
        VALIDATION_MUSIC_BAND = new ValidationMusicBand(SERVER_COMMANDS.keySet());
        scanner = new Scanner(System.in);
    }

    public void startInputHandler() {
        System.out.println(GREETING);
        try {
            readFromScanner();
        } catch (IllegalStateException e) {
            log.debug(CLOSE_TEXT);
        }
    }

    public void startInputHandler(Scanner scanner) {
        InputHandler.setScanner(scanner);
        try {
            readFromScanner();
        } catch (IllegalStateException e) {
            log.debug(CLOSE_TEXT);
        }
    }

    private void readFromScanner() {
        while (scanner.hasNextLine()) {
            try {
                String[] inputData = scanner.nextLine().split(" ");
                String commandName = inputData[0];
                String[] args = Arrays.copyOfRange(inputData, 1, inputData.length);

                if (CLIENT_COMMAND.containsKey(commandName)) {
                    CLIENT_COMMAND.get(commandName).execute(args);
                } else {
                    ClientResponse response = (ClientResponse) new ConnectionToServer()
                            .interactionWithServer(createBodyRequest(commandName, args));
                    System.out.print(response.answer());
                }
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.out.println(HELP_TEXT);
            } catch (UnexpectedCommandExceptions | IncorrectDataEntryExceptions e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static ClientRequest createBodyRequest(String commandName, String[] args) throws UnexpectedCommandExceptions,
            IncorrectDataEntryExceptions {
        ClientRequest.ClientRequestBuilder clientRequest = ClientRequest.builder();

        if (!SERVER_COMMANDS.containsKey(commandName)) {
            throw new NullPointerException();
        }

        if (SERVER_COMMANDS.get(commandName).get("firstGetCommand")) {
            postServerAdditionalRequest(commandName, args);
        }
        if (SERVER_COMMANDS.get(commandName).get("transmitObject")) {
            MusicBand musicBand = new CreateMusicBand(VALIDATION_MUSIC_BAND).create(scanner);
            clientRequest.musicBand(musicBand);
        }

        return clientRequest
                .commandName(commandName)
                .argument(args)
                .build();
    }

    public static void postServerAdditionalRequest(String commandName, String[] args)
            throws IncorrectDataEntryExceptions {
        ClientResponse additionalCheckArgs = (ClientResponse) new ConnectionToServer()
                .interactionWithServer(
                        ClientRequest.builder()
                                .commandName(commandName)
                                .argument(args)
                                .build()
                );

        if (additionalCheckArgs.answer() != null && additionalCheckArgs.answer().length() != 0) {
            throw new IncorrectDataEntryExceptions(additionalCheckArgs.answer());
        }
    }
}
