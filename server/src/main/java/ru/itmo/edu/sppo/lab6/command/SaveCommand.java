package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.document.WriteXml;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import javax.xml.stream.XMLStreamException;

public class SaveCommand implements BaseCommand {
    private static final String NAME = "save";
    private static final String SUCCESS = "Сохрание в файл прошло успешно";
    private final WriteXml writer = new WriteXml();

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> сохраняет коллекцию в файл";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions {
        try {
            writer.writeFile(printer);
            printer.println(SUCCESS);
        } catch (XMLStreamException e) {
            printer.println(e.getMessage());
        }
        return new ClientResponse(printer.toString());
    }
}
