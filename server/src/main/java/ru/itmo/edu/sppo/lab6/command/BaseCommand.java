package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.GetServerCommands;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.sql.SQLException;
import java.util.Map;

public interface BaseCommand {
    String ERROR_MESSAGE_LOTS_OF_ARGS = "Эта команда должна использоваться без аргументов";

    String getCommandName();

    String getCommandDescription();

    default Map<String, Boolean> getDetailsFromClient() {
        return GetServerCommands.getTemplateDetails();
    }

    ClientResponse execute(ClientRequest request, Printer printer)
            throws IncorrectDataEntryExceptions, UnexpectedCommandExceptions, SQLException;

    default void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 0) {
            throw new IncorrectDataEntryExceptions(ERROR_MESSAGE_LOTS_OF_ARGS);
        }
    }
}
