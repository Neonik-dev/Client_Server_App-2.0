package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.database.service.service.UserSessionService;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.storage.SingletonJdbcServices;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClearCommand implements BaseCommand {
    private static final String NAME = "clear";
    private static final String SUCCESS = "Все ваши элементы успешно удалились из коллекции";
    private static final MusicBandService MUSIC_BAND_SERVICE = SingletonJdbcServices.getMUSIC_BAND_SERVICE();
    private static final UserSessionService USER_SESSION_SERVICE = SingletonJdbcServices.getUSER_SESSION_SERVICE();

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " -> очищает коллекцию";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer) throws IncorrectDataEntryExceptions,
            UnexpectedCommandExceptions, SQLException {
        checkArgs(request.getArgument());
        int userId = USER_SESSION_SERVICE.getUserIdBySession(request.getSession());
        ArrayList<Integer> arrMusicBandIds = MUSIC_BAND_SERVICE.deleteByUserId(userId);
        MusicBandCollection.delete(arrMusicBandIds);

        printer.println(SUCCESS);
        return new ClientResponse(printer.toString());
    }
}
