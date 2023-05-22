package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.command.Commands;

import java.util.HashMap;
import java.util.Map;

public class GetServerCommands {
    private static final Map<String, Boolean> SERVER_COMMANDS = new HashMap<>();

    static {
        new Commands().getAllCommand().forEach(
                (key, value) -> SERVER_COMMANDS.put(key, value.needToTransferCollectionItem())
        );
    }

    public static Map<String, Boolean> get() {
        return SERVER_COMMANDS;
    }
}
