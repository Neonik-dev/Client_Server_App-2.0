package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.InputHandler;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;

public class ExitCommand implements BaseCommand {
    private static final String NAME = "exit";
    private static final String EXIT_TEXT = "Клиент завершает свою работу. До связи\uD83E\uDD19!";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> завершает программу";
    }

    @Override
    public void execute(String[] args) throws IncorrectDataEntryExceptions {
        checkArgs(args);
        System.out.println(EXIT_TEXT);
        InputHandler.getScanner().close();
    }
}
