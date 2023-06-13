package ru.itmo.edu.sppo.lab6.storage;

import ru.itmo.edu.sppo.lab6.command.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Commands {
    private static final Map<String, BaseCommand> COMMANDS = new HashMap<>();

    public Commands() {
        if (COMMANDS.isEmpty()) {
            BaseCommand infoCommand = new InfoCommand();
            COMMANDS.put(infoCommand.getCommandName(), infoCommand);

            BaseCommand helpCommand = new HelpCommand();
            COMMANDS.put(helpCommand.getCommandName(), helpCommand);

            BaseCommand clearCommand = new ClearCommand();
            COMMANDS.put(clearCommand.getCommandName(), clearCommand);

            BaseCommand historyCommand = new HistoryCommand();
            COMMANDS.put(historyCommand.getCommandName(), historyCommand);

            BaseCommand addCommand = new AddCommand();
            COMMANDS.put(addCommand.getCommandName(), addCommand);

            BaseCommand showCommand = new ShowCommand();
            COMMANDS.put(showCommand.getCommandName(), showCommand);

            BaseCommand updateCommand = new UpdateCommand();
            COMMANDS.put(updateCommand.getCommandName(), updateCommand);

            BaseCommand removeByIdCommand = new RemoveByIdCommand();
            COMMANDS.put(removeByIdCommand.getCommandName(), removeByIdCommand);

            BaseCommand removeHeadCommand = new RemoveHeadCommand();
            COMMANDS.put(removeHeadCommand.getCommandName(), removeHeadCommand);

            BaseCommand printUniqueNumberOfParticipantsCommand = new PrintUniqueNumberOfParticipantsCommand();
            COMMANDS.put(
                    printUniqueNumberOfParticipantsCommand.getCommandName(),
                    printUniqueNumberOfParticipantsCommand
            );

            BaseCommand countLessThanNumberOfParticipantsCommand = new CountLessThanNumberOfParticipantsCommand();
            COMMANDS.put(
                    countLessThanNumberOfParticipantsCommand.getCommandName(),
                    countLessThanNumberOfParticipantsCommand
            );

            BaseCommand saveCommand = new SaveCommand();
            COMMANDS.put(saveCommand.getCommandName(), saveCommand);

            BaseCommand printEstablishmentDateSortAscCommand = new PrintEstablishmentDateSortAscCommand();
            COMMANDS.put(printEstablishmentDateSortAscCommand.getCommandName(), printEstablishmentDateSortAscCommand);

            BaseCommand registrationCommand = new RegistrationCommand();
            COMMANDS.put(registrationCommand.getCommandName(), registrationCommand);

            BaseCommand authorizationCommand = new AuthorizationCommand();
            COMMANDS.put(authorizationCommand.getCommandName(), authorizationCommand);
        }
    }

    public Map<String, BaseCommand> getAllCommand() {
        return COMMANDS.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
