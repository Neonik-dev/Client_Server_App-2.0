package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.util.Map;

public class HelpCommand implements BaseCommand {
    private static final boolean NEED_TRANSFER_ELEMENT = false;
    private static final Map<String, BaseCommand> COMMANDS = new Commands().getAllCommand();
    private final String name = "help";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandDescription() {
        return name + " -> выводит справку по всем доступным командам";
    }

    @Override
    public boolean needToTransferCollectionItem() {
        return NEED_TRANSFER_ELEMENT;
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        for (String key : COMMANDS.keySet()) {
            printer.println(COMMANDS.get(key).getCommandDescription());
        }
        return new ClientResponse(printer.toString());
    }
}
