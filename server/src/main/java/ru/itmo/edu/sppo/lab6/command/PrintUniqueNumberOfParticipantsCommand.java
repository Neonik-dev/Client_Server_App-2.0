package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public class PrintUniqueNumberOfParticipantsCommand implements BaseCommand {
    private static final String NAME = "print_unique_number_of_participants";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> выводит уникальные значения поля numberOfParticipants всех элементов в коллекции";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions {
        checkArgs(request.getArgument());
        MusicBandCollection.getUniqueNumberOfParticipants(printer);

        return new ClientResponse(printer.toString());
    }
}
