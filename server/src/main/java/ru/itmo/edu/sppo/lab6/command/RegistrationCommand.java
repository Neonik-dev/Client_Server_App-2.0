package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.GenerateSession;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public class RegistrationCommand implements BaseCommand {
    private static final String NAME = "registration";
    private static final String ERROR_MESSAGE_COUNT_ARGS = "Для этой команды необходимо 2 аргумента: логин и пароль";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " login password -> регистрирует пользователя";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions {
        checkArgs(request.getArgument());
        return new ClientResponse(GenerateSession.generate());
    }

    @Override
    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 2) {
            throw new IncorrectDataEntryExceptions(ERROR_MESSAGE_COUNT_ARGS);
        }
    }
}
