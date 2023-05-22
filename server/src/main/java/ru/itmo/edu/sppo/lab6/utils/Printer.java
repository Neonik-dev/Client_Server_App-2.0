package ru.itmo.edu.sppo.lab6.utils;

public class Printer {
    private final StringBuffer messageToClient;

    public Printer() {
        messageToClient = new StringBuffer();
    }

    public Printer(String message) {
        messageToClient = new StringBuffer(message);
    }

    public void println(String message) {
        messageToClient.append(message).append("\n");
    }

    public void print(String message) {
        messageToClient.append(message);
    }

    @Override
    public String toString() {
        return messageToClient.toString();
    }
}
