package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.dto.collectionitem.MusicBand;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.util.LinkedList;

public class InfoCommand implements BaseCommand {
    private static final boolean NEED_TRANSFER_ELEMENT = false;
    private final String name = "info";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandDescription() {
        return name + " -> выводит информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public boolean needToTransferCollectionItem() {
        return NEED_TRANSFER_ELEMENT;
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        LinkedList<MusicBand> myCollections = MusicBandCollection.getMusicBandLinkedList();
        printer.println("Тип коллекции -> " + myCollections.getClass().getName());
        printer.println("Дата инициализации коллекции -> " + MusicBandCollection.getDateCreated());
        printer.println("Колличество элементов в коллекции -> " + myCollections.size());
        return new ClientResponse(printer.toString());
    }
}
