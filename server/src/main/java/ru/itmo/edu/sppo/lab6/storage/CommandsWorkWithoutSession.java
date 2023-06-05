package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.command.AuthorizationCommand;
import ru.itmo.edu.sppo.lab6.command.RegistrationCommand;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandsWorkWithoutSession {
    private static final Set<String> commands = Stream.of(
            new RegistrationCommand().getCommandName(),
            new AuthorizationCommand().getCommandName()
    ).collect(Collectors.toSet());

    private CommandsWorkWithoutSession() {
    }

    public static Set<String> getCommands() {
        return commands;
    }
}