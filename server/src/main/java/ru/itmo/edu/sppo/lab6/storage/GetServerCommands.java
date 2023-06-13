package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.command.SaveCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GetServerCommands {
    private static final String[] EXCLUDE_COMMANDS = {new SaveCommand().getCommandName()};
    private static final Map<String, Map<String, Boolean>> SERVER_COMMANDS = new HashMap<>();
    private static final Map<String, Boolean> TEMPLATE_DETAILS = Map.of(
            "transmitObject", false,
            "firstGetCommand", false
    );

    static {
        new Commands().getAllCommand().forEach(
                (key, value) -> SERVER_COMMANDS.put(key, value.getDetailsFromClient())
        );
        Arrays.stream(EXCLUDE_COMMANDS).forEach(SERVER_COMMANDS::remove);
    }

    private GetServerCommands() {
    }

    public static Map<String, Boolean> getTemplateDetails() {
        return new HashMap<>(TEMPLATE_DETAILS);
    }

    public static Map<String, Map<String, Boolean>> getDetails() {
        return SERVER_COMMANDS;
    }

    public static String[] getExcludeCommands() {
        return EXCLUDE_COMMANDS;
    }
}
