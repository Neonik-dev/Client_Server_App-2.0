package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.database.service.service.UserSessionService;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.storage.SingletonJdbcServices;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.sql.SQLException;

public class RemoveHeadCommand implements BaseCommand {
    private static final String NAME = "remove_head";
    private static final MusicBandService MUSIC_BAND_SERVICE = SingletonJdbcServices.getMUSIC_BAND_SERVICE();
    private static final UserSessionService USER_SESSION_SERVICE = SingletonJdbcServices.getUSER_SESSION_SERVICE();

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> выводит и удаляет первый элемент коллекции";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions, SQLException {
        checkArgs(request.getArgument());

        int userId = USER_SESSION_SERVICE.getUserIdBySession(request.getSession());
        MusicBand musicBand = MUSIC_BAND_SERVICE.deleteAndReturnHeadByUserId(userId);
        MusicBandCollection.delete(musicBand.getId());

        printer.println(musicBand.toString());
        return new ClientResponse(printer.toString());
    }
}
