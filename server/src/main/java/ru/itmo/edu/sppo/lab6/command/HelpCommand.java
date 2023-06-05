package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.Commands;
import ru.itmo.edu.sppo.lab6.storage.GetServerCommands;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.util.Arrays;
import java.util.Map;

public class HelpCommand implements BaseCommand {
    private static Map<String, BaseCommand> commands;
    private static final String NAME = "help";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> выводит справку по всем доступным командам";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        getCommands();

        commands.keySet().forEach(
                key -> printer.println(commands.get(key).getCommandDescription())
        );
        return new ClientResponse(printer.toString());
    }

    public void getCommands() {
        if (commands == null) {
            commands = new Commands().getAllCommand();
            Arrays.stream(GetServerCommands.getExcludeCommands()).forEach(commands::remove);
        }
    }
}
