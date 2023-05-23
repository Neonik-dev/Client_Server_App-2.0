package ru.itmo.edu.sppo.lab6.command;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    private static final Map<String, BaseCommand> COMMANDS = new HashMap<>();

    public Commands() {
        if (COMMANDS.isEmpty()) {
            BaseCommand infoCommand = new InfoCommand();
            COMMANDS.put(infoCommand.getCommandName(), infoCommand);

            BaseCommand helpCommand = new HelpCommand();
            COMMANDS.put(helpCommand.getCommandName(), helpCommand);

//            BaseCommand clearCommand = new ClearCommand();
//            COMMANDS.put(clearCommand.getName(), clearCommand);

            BaseCommand historyCommand = new HistoryCommand();
            COMMANDS.put(historyCommand.getCommandName(), historyCommand);

            BaseCommand addCommand = new AddCommand();
            COMMANDS.put(addCommand.getCommandName(), addCommand);

            BaseCommand showCommand = new ShowCommand();
            COMMANDS.put(showCommand.getCommandName(), showCommand);

//            BaseCommand updateCommand = new UpdateCommand();
//            COMMANDS.put(updateCommand.getName(), updateCommand);
//
//            BaseCommand removeByIdCommand = new RemoveByIdCommand();
//            COMMANDS.put(removeByIdCommand.getName(), removeByIdCommand);
//
//            BaseCommand exitCommand = new ExitCommand();
//            COMMANDS.put(exitCommand.getName(), exitCommand);
//
//            BaseCommand removeHeadCommand = new RemoveHeadCommand();
//            COMMANDS.put(removeHeadCommand.getName(), removeHeadCommand);
//
//            BaseCommand printUniqueNumberOfParticipantsCommand = new PrintUniqueNumberOfParticipantsCommand();
//            COMMANDS.put(printUniqueNumberOfParticipantsCommand.getName(), printUniqueNumberOfParticipantsCommand);
//
//            BaseCommand countLessThanNumberOfParticipantsCommand = new CountLessThanNumberOfParticipantsCommand();
//            COMMANDS.put(countLessThanNumberOfParticipantsCommand.getName(), countLessThanNumberOfParticipantsCommand);
//
//            BaseCommand saveCommand = new SaveCommand();
//            COMMANDS.put(saveCommand.getName(), saveCommand);
//
//            BaseCommand executeScriptCommand = new ExecuteScriptCommand();
//            COMMANDS.put(executeScriptCommand.getName(), executeScriptCommand);
//
//            BaseCommand printFieldAscendingEstablishmentDateCommand = new PrintFieldAscendingEstablishmentDateCommand();
//            COMMANDS.put(printFieldAscendingEstablishmentDateCommand.getName(), printFieldAscendingEstablishmentDateCommand);
        }
    }

    public Map<String, BaseCommand> getAllCommand() {
        return COMMANDS;
    }
}
