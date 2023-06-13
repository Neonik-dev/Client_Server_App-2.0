package ru.itmo.edu.sppo.lab6;

import ru.itmo.edu.sppo.lab6.command.BaseCommand;
import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;
import ru.itmo.edu.sppo.lab6.storage.Commands;
import ru.itmo.edu.sppo.lab6.command.SaveCommand;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.CommandsWorkWithoutSession;
import ru.itmo.edu.sppo.lab6.storage.GetServerCommands;
import ru.itmo.edu.sppo.lab6.storage.HistoryStorage;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class InputHandler implements Callable<Object> {
    public static final String GET_ALL_COMMANDS = "getAllCommands";
    private static final String RECOMMENDATION_HELP_COMMAND =
            "Напишите любую команду из списка. Чтобы посмотреть список команд воспользуйтесь командой -> help";
    private static final Map<String, BaseCommand> COMMANDS = new Commands().getAllCommand();
    private static final Set<String> COMMANDS_WITHOUT_SESSION = CommandsWorkWithoutSession.getCommands();
    private static final BaseCommand SAVE_COMMAND = new SaveCommand();
    private final ClientRequest request;

    public InputHandler(ClientRequest request) {
        this.request = request;
    }

    @Override
    public Object call() {
        String exception = null;
        Printer printer = new Printer();
        try {
            return choiceCommand(request, printer);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            printer.println(RECOMMENDATION_HELP_COMMAND);
        } catch (AuthorizationException e) {
            printer.println(e.getMessage());
            exception = e.getClass().getSimpleName();
        } catch (IncorrectDataEntryExceptions | UnexpectedCommandExceptions | SQLException e) {
            printer.println(e.getMessage());
        }
        return new ClientResponse(printer.toString(), exception);
    }

    private static Object choiceCommand(ClientRequest request, Printer printer) throws UnexpectedCommandExceptions,
            IncorrectDataEntryExceptions, SQLException {
        BaseCommand command = COMMANDS.get(request.getCommandName());
        if (request.getCommandName().equals(GET_ALL_COMMANDS)) {
            return GetServerCommands.getDetails();
        } else if (command == null) {
            throw new NullPointerException();
        } else {
            checkSession(request);
            HistoryStorage.add(request.getCommandName());
            ClientResponse response = command.execute(request, printer);
            SAVE_COMMAND.execute(request, printer);
            return response;
        }
    }

    private static void checkSession(ClientRequest request) throws AuthorizationException {
        if (!COMMANDS_WITHOUT_SESSION.contains(request.getCommandName()) && request.getSession() == null) {
            throw new AuthorizationException("Пользователь не авторизован. Команды не доступны.");
        }
    }
}
