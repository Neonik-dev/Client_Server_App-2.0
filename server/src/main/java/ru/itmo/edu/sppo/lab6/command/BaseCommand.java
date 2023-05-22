package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public interface BaseCommand {
    String ERROR_MESSAGE_LOTS_OF_ARGS = "Эта команда должна использоваться без аргументов";

    String getCommandName();

    String getCommandDescription();

    boolean needToTransferCollectionItem();

    ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions;

    default void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 0) {
            throw new IncorrectDataEntryExceptions(ERROR_MESSAGE_LOTS_OF_ARGS);
        }
    }
}
