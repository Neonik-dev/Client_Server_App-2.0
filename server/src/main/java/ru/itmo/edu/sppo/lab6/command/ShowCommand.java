package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public class ShowCommand implements BaseCommand {
    private static final String NAME = "show";
    private static final boolean NEED_TRANSFER_ELEMENT = false;

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> выводит все элементы коллекции";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        MusicBandCollection.show(printer);
        return new ClientResponse(printer.toString());
    }

    @Override
    public boolean needToTransferCollectionItem() {
        return NEED_TRANSFER_ELEMENT;
    }
}
