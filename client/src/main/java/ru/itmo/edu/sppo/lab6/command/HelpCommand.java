package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.InputHandler;
import ru.itmo.edu.sppo.lab6.client.ConnectionToServer;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;

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
        return null;
    }

    @Override
    public void execute(String[] args) throws IncorrectDataEntryExceptions, UnexpectedCommandExceptions {
        checkArgs(args);
        getCommands();
        ClientResponse response = (ClientResponse) new ConnectionToServer()
                .interactionWithServer(InputHandler.createBodyRequest(NAME, args));
        System.out.print(response.answer());
        commands.keySet().forEach(
                key -> System.out.println(commands.get(key).getCommandDescription())
        );
    }

    public void getCommands() {
        if (commands == null) {
            commands = new Commands().getAllCommand();
            commands.remove(NAME);
        }
    }
}
