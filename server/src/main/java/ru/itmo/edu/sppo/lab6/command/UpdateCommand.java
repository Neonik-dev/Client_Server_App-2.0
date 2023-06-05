package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.GetServerCommands;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.CheckID;
import ru.itmo.edu.sppo.lab6.utils.CheckNumberOfArguments;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.util.Map;

public class UpdateCommand implements BaseCommand {
    private static final String SUCCESS = "Элемент коллекции успешно обновился";
    private static final String TEXT_CHECK_ARGUMENTS = "Необходимо ввести один числовой аргумент -> id";
    private static final String NAME = "update";
    private static final boolean TRANSMIT_OBJECT = true;
    private static final boolean FIRST_GET_COMMAND = true;

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " id -> обновляет значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public Map<String, Boolean> getDetailsFromClient() {
        Map<String, Boolean> details = GetServerCommands.getTemplateDetails();
        details.put("transmitObject", TRANSMIT_OBJECT);
        details.put("firstGetCommand", FIRST_GET_COMMAND);
        return details;
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions {
        checkArgs(request.getArgument());
        if (request.getMusicBand() != null) {
            MusicBandCollection.updateItem(request.getMusicBand(), Integer.parseInt(request.getArgument()[0]));
            printer.println(SUCCESS);
        }
        return new ClientResponse(printer.toString());
    }

    @Override
    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        CheckNumberOfArguments.check(args, 1, TEXT_CHECK_ARGUMENTS);
        CheckID.checkExistsID(args[0]);
    }
}
