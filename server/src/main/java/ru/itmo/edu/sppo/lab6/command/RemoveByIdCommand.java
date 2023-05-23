package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.CheckID;
import ru.itmo.edu.sppo.lab6.utils.Printer;

public class RemoveByIdCommand implements BaseCommand {
    private static final String NAME = "remove_by_id";
    private static final String SUCCESS = "Удаление элемента с (id)=(%d) прошло успешно";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " id ->  удаляет элемент из коллекции по его id";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions {
        checkArgs(request.getArgument());
        int id = Integer.parseInt(request.getArgument()[0]);
        MusicBandCollection.delete(id);
        printer.println(String.format(SUCCESS, id));

        return new ClientResponse(printer.toString());
    }

    @Override
    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        if (args.length != 1) {
            throw new IncorrectDataEntryExceptions("Необходимо ввести один числовой аргумент -> id");
        }
        CheckID.checkExistsID(args[0]);
    }
}
