package ru.itmo.edu.sppo.lab6.exceptions;

public class AuthorizationException extends IncorrectDataEntryExceptions {
    public AuthorizationException(String message) {
        super(message);
    }
}
