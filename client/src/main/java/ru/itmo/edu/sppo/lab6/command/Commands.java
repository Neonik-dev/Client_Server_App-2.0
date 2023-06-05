package ru.itmo.edu.sppo.lab6.command;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Commands {
    private static final Map<String, BaseCommand> COMMANDS = new HashMap<>();

    public Commands() {
        if (COMMANDS.isEmpty()) {
            BaseCommand exitCommand = new ExitCommand();
            COMMANDS.put(exitCommand.getCommandName(), exitCommand);

            BaseCommand executeScriptCommand = new ExecuteScriptCommand();
            COMMANDS.put(executeScriptCommand.getCommandName(), executeScriptCommand);

            BaseCommand helpCommand = new HelpCommand();
            COMMANDS.put(helpCommand.getCommandName(), helpCommand);

            BaseCommand authorizationCommand = new AuthorizationCommand();
            COMMANDS.put(authorizationCommand.getCommandName(), authorizationCommand);

            BaseCommand registrationCommand = new RegistrationCommand();
            COMMANDS.put(registrationCommand.getCommandName(), registrationCommand);
        }
    }

    public Map<String, BaseCommand> getAllCommand() {
        return COMMANDS.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
