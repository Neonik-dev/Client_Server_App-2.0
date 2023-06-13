package ru.itmo.edu.sppo.lab6.utils;

public class GenerateSession {
    private static final int SESSION_SIZE = 35;
    private static final char[] ALPHABET =
            "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private GenerateSession() {
    }

    public static String generate() {
        char[] session = new char[SESSION_SIZE];
        for (int i = 0; i < session.length; i++) {
            int index = (int) (Math.random() * ALPHABET.length);
            session[i] = ALPHABET[index];
        }
        return String.valueOf(session);
    }
}
