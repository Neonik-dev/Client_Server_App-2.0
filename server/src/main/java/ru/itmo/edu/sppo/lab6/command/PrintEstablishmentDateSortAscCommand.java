package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.util.Comparator;
import java.util.LinkedList;

public class PrintEstablishmentDateSortAscCommand implements BaseCommand {
    private final static String NAME = "print_establishment_date_sort_asc_command";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> выводит значения поля establishmentDate всех элементов в порядке возрастания";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions {
        checkArgs(request.getArgument());

        Comparator<MusicBand> comparator = (item1, item2) -> {
            if (item1.getSafeEstablishmentDate().isEmpty() || item2.getSafeEstablishmentDate().isEmpty()) {
                return 0;
            }
            return item1.getSafeEstablishmentDate().get().compareTo(item2.getSafeEstablishmentDate().get());
        };

        LinkedList<MusicBand> musicBandCollection = MusicBandCollection.getMusicBandCollection();
        musicBandCollection.sort(comparator);
        musicBandCollection.stream()
                .filter(
                        musicBand -> musicBand.getSafeEstablishmentDate().isPresent()
                ).forEach(
                        musicBand -> printer.println(
                                musicBand.getSafeEstablishmentDate().get().toString()
                        )
                );

        return new ClientResponse(printer.toString());
    }
}
