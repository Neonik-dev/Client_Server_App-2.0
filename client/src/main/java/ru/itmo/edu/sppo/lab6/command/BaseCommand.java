package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;

public interface BaseCommand {
    String ERROR_MESSAGE_LOTS_OF_ARGS = "Эта команда должна использоваться без аргументов";

    String getCommandName();

    String getCommandDescription();

    void execute(String[] args) throws IncorrectDataEntryExceptions;

    default void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 0) {
            throw new IncorrectDataEntryExceptions(ERROR_MESSAGE_LOTS_OF_ARGS);
        }
    }
}
