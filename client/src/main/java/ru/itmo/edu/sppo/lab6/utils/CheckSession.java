package ru.itmo.edu.sppo.lab6.utils;

public class CheckSession {
    private CheckSession() {
    }

    public static boolean isGoodSession(String exception) {
        return exception == null || exception.isEmpty();
    }
}
