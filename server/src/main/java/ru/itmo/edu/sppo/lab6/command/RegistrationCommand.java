package ru.itmo.edu.sppo.lab6.command;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.database.repository.jdbc.JdbcUserSessionRepository;
import ru.itmo.edu.sppo.lab6.database.repository.jdbc.JdbcUsersRepository;
import ru.itmo.edu.sppo.lab6.database.service.jdbc.JdbcUsersService;
import ru.itmo.edu.sppo.lab6.database.service.service.UsersService;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.GenerateSession;
import ru.itmo.edu.sppo.lab6.utils.PasswordHash;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.sql.SQLException;

@Slf4j
public class RegistrationCommand implements BaseCommand {
    private static final String NAME = "registration";
    private static final String ERROR_MESSAGE_COUNT_ARGS = "Для этой команды необходимо 2 аргумента: логин и пароль";
    private static final UsersService USERS_SERVICE = new JdbcUsersService(
            new JdbcUsersRepository(), new JdbcUserSessionRepository()
    );

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
        String[] args = request.getArgument();
        checkArgs(args);
        String session = GenerateSession.generate();
        try {
            USERS_SERVICE.register(args[0], PasswordHash.generateHashSha384(args[1]), session);
        } catch (SQLException e) {
            throw new AuthorizationException(e.getMessage());
        }

        return new ClientResponse(session);
    }

    @Override
    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 2) {
            throw new IncorrectDataEntryExceptions(ERROR_MESSAGE_COUNT_ARGS);
        }
    }
}
