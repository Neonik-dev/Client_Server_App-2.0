package ru.itmo.edu.sppo.lab6.command;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.GetServerCommands;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.util.Map;

@Slf4j
public class AddCommand implements BaseCommand {
    private static final String SUCCESS_ADD = "Элемент успешно добавился в коллекцию";
    private static final boolean TRANSMIT_OBJECT = true;
    private static final String NAME = "add";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> добавляет новый элемент в коллекцию";
    }

    @Override
    public Map<String, Boolean> getDetailsFromClient() {
        Map<String, Boolean> details = GetServerCommands.getTemplateDetails();
        details.put("transmitObject", TRANSMIT_OBJECT);
        return details;
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions {
        checkArgs(request.getArgument());
        MusicBandCollection.add(request.getMusicBand());

        log.info(MusicBandCollection.getMusicBandCollection().toString());
        printer.println(SUCCESS_ADD);
        return new ClientResponse(printer.toString());
    }

}
