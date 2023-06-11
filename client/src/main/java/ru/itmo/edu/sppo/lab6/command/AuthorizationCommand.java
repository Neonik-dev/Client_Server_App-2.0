package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.InputHandler;
import ru.itmo.edu.sppo.lab6.client.InteractionWithServer;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.CheckSession;

public class AuthorizationCommand implements BaseCommand {
    private static final String NAME = "authorization";
    private static final String ERROR_MESSAGE_COUNT_ARGS = "Для этой команды необходимо 2 аргумента: логин и пароль";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " login password -> вход в аккаунт";
    }

    @Override
    public void execute(String[] args) throws IncorrectDataEntryExceptions {
        checkArgs(args);
        ClientResponse response = (ClientResponse) new InteractionWithServer()
                .interaction(
                        ClientRequest.builder()
                                .commandName(NAME)
                                .argument(new String[]{args[0], args[1]})
                                .build()
                );

        if (CheckSession.isGoodSession(response.exception())) {
            InputHandler.setSession(response.answer());
            System.out.printf("Пользователь %s успешно авторизовался%n", args[0]);
        } else {
            System.out.printf("Авторизация не удалась (details) = %s", response.answer());
        }
    }

    @Override
    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 2) {
            throw new IncorrectDataEntryExceptions(ERROR_MESSAGE_COUNT_ARGS);
        }
    }
}
