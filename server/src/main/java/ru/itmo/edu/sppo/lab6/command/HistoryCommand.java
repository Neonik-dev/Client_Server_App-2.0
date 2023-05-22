package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.HistoryStorage;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public class HistoryCommand implements BaseCommand {
    private static final boolean NEED_TRANSFER_ELEMENT = false;
    private final String name = "history";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandDescription() {
        return name + " -> выводит последние" + HistoryStorage.STORAGE_SIZE + "команд (без их аргументов)";
    }

    @Override
    public boolean needToTransferCollectionItem() {
        return NEED_TRANSFER_ELEMENT;
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        printer.println(HistoryStorage.printHistory());
        return new ClientResponse(printer.toString());
    }
}
