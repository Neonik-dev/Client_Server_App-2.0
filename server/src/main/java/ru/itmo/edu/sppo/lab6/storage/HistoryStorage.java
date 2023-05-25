package ru.itmo.edu.sppo.lab6.storage;

import java.util.LinkedList;
import java.util.Queue;

public class HistoryStorage {
    public static final int STORAGE_SIZE = 13;
    private static final Queue<String> HISTORY = new LinkedList<>();

    public static void add(String command) {
        if (HISTORY.size() == STORAGE_SIZE) {
            HISTORY.poll();
        }
        HISTORY.add(command);
    }

    public static String printHistory() {
        StringBuilder text = new StringBuilder();
        int commandNumber = 1;
        for (String command : HISTORY) {
            text.append(commandNumber).append(" - ").append(command).append("\n");
            commandNumber++;
        }
        text.deleteCharAt(text.length() - 1);
        return text.toString();
    }

    public void clear() {
        HISTORY.clear();
    }
}
