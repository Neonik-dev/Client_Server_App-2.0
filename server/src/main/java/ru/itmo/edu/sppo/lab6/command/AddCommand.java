package ru.itmo.edu.sppo.lab6.command;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.Printer;

@Slf4j
public class AddCommand implements BaseCommand {
    private static final boolean NEED_TRANSFER_ELEMENT = true;
    private final String name = "add";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandDescription() {
        return name + " -> добавляет новый элемент в коллекцию";
    }

    @Override
    public boolean needToTransferCollectionItem() {
        return NEED_TRANSFER_ELEMENT;
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        MusicBandCollection.add(request.getMusicBand());

        log.info(MusicBandCollection.getMusicBandLinkedList().toString());
        return new ClientResponse(printer.toString());
    }

}
