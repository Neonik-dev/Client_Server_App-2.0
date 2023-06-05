package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.HistoryStorage;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public class HistoryCommand implements BaseCommand {
    private static final String NAME = "history";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> выводит последние " + HistoryStorage.STORAGE_SIZE + " команд (без их аргументов)";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        printer.println(HistoryStorage.printHistory());
        return new ClientResponse(printer.toString());
    }
}
