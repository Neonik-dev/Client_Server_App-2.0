package ru.itmo.edu.sppo.lab6;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.client.InteractionWithServer;
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
    @Getter
    @Setter
    private static String session;
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
        setScanner(new Scanner(System.in));
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
        while (getScanner().hasNextLine()) {
            try {
                String[] inputData = getScanner().nextLine().split(" ");
                String commandName = inputData[0];
                String[] args = Arrays.copyOfRange(inputData, 1, inputData.length);

                if (CLIENT_COMMAND.containsKey(commandName)) {
                    CLIENT_COMMAND.get(commandName).execute(args);
                } else {
                    ClientResponse response = (ClientResponse) new InteractionWithServer()
                            .interaction(createBodyRequest(commandName, args));
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
        if (!SERVER_COMMANDS.containsKey(commandName)) {
            throw new NullPointerException();
        }

        if (SERVER_COMMANDS.get(commandName).get("firstGetCommand")) {
            postServerAdditionalRequest(commandName, args);
        }
        MusicBand musicBand = null;
        if (SERVER_COMMANDS.get(commandName).get("transmitObject")) {
            musicBand = new CreateMusicBand(VALIDATION_MUSIC_BAND).create(getScanner());
        }

        return ClientRequest.builder()
                .commandName(commandName)
                .argument(args)
                .musicBand(musicBand)
                .session(getSession())
                .build();
    }

    public static void postServerAdditionalRequest(String commandName, String[] args)
            throws IncorrectDataEntryExceptions {
        ClientResponse additionalCheckArgs = (ClientResponse) new InteractionWithServer()
                .interaction(
                        ClientRequest.builder()
                                .commandName(commandName)
                                .argument(args)
                                .session(getSession())
                                .build()
                );

        if (additionalCheckArgs.answer() != null && additionalCheckArgs.answer().length() != 0) {
            throw new IncorrectDataEntryExceptions(additionalCheckArgs.answer());
        }
    }
}
