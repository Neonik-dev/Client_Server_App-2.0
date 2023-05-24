package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.CheckNumberOfArguments;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public class CountLessThanNumberOfParticipantsCommand implements BaseCommand {
    private static final String TEXT_CHECK_ARGUMENTS =
            "Необходимо ввести один числовой аргумент -> numberOfParticipants";
    private static final String NAME = "count_less_than_number_of_participants";
    private static final String TEXT_ANSWER = "%d -> столько музыкальных групп имеют количество участников меньше %d";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " numberOfParticipants -> выводит количество элементов,"
                + " значение поля numberOfParticipants которых меньше заданного";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions {
        checkArgs(request.getArgument());
        long participants = Long.parseLong(request.getArgument()[0]);

        printer.println(
                String.format(
                        TEXT_ANSWER,
                        MusicBandCollection.countNumberOfParticipants(participants),
                        participants
                )
        );
        return new ClientResponse(printer.toString());
    }

    @Override
    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        CheckNumberOfArguments.check(args, 1, TEXT_CHECK_ARGUMENTS);

        try {
            long number = Long.parseLong(args[0]);
            if (number <= 0) {
                throw new IncorrectDataEntryExceptions("Количество участников должно быть больше 0");
            }
        } catch (NumberFormatException e) {
            throw new IncorrectDataEntryExceptions("Аргумент numberOfParticipants не является целым числом");
        }
    }
}
