package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.InputHandler;
import ru.itmo.edu.sppo.lab6.client.InteractionWithServer;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.CheckSession;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelpCommand implements BaseCommand {
    private static final Set<String> EXCLUDE_COMMANDS = Stream.of(
            new AuthorizationCommand().getCommandName(),
            new RegistrationCommand().getCommandName(),
            new HelpCommand().getCommandName()
    ).collect(Collectors.toSet());
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
    public void execute(String[] args) throws IncorrectDataEntryExceptions {
        checkArgs(args);
        try {
            getCommands();
            ClientResponse response = (ClientResponse) new InteractionWithServer()
                    .interaction(InputHandler.createBodyRequest(NAME, args));

            Consumer<String> lambdaForEach = key -> System.out.println(commands.get(key).getCommandDescription());
            if (CheckSession.isGoodSession(response.exception())) {
                commands.keySet().stream()
                        .filter(key -> !EXCLUDE_COMMANDS.contains(key))
                        .forEach(lambdaForEach);
            } else {
                commands.keySet().forEach(lambdaForEach);
            }
        } catch (UnexpectedCommandExceptions e) {
            throw new RuntimeException();
        }
    }

    public void getCommands() {
        if (commands == null) {
            commands = new Commands().getAllCommand();
        }
    }
}
