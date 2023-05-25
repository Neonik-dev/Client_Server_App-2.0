package ru.itmo.edu.sppo.lab6.utils;

import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;

public class CheckNumberOfArguments {
    private CheckNumberOfArguments() {
    }

    public static void check(String[] args, int quantity, String errorMessage) throws IncorrectDataEntryExceptions {
        if (args.length != quantity) {
            throw new IncorrectDataEntryExceptions(errorMessage);
        }
    }
}
