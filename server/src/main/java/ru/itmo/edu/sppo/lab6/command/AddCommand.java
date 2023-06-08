package ru.itmo.edu.sppo.lab6.command;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.database.service.service.UserSessionService;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.UnexpectedCommandExceptions;
import ru.itmo.edu.sppo.lab6.storage.GetServerCommands;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.SingletonJdbcServices;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class AddCommand implements BaseCommand {
    private static final String SUCCESS_ADD = "Элемент успешно добавился в коллекцию";
    private static final boolean TRANSMIT_OBJECT = true;
    private static final String NAME = "add";
    private static final MusicBandService MUSIC_BAND_SERVICE = SingletonJdbcServices.getMUSIC_BAND_SERVICE();
    private static final UserSessionService USER_SESSION_SERVICE = SingletonJdbcServices.getUSER_SESSION_SERVICE();

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
            UnexpectedCommandExceptions, SQLException {
        checkArgs(request.getArgument());
        MusicBand musicBand = request.getMusicBand();
        MusicBandCollection.VALIDATION_MUSIC_BAND.checkMusicBand(musicBand);

        int userId =  USER_SESSION_SERVICE.getUserIdBySession(request.getSession());
        MusicBand musicBandFromDB = MUSIC_BAND_SERVICE.add(musicBand, userId);
        MusicBandCollection.add(musicBandFromDB);

        log.info(MusicBandCollection.getMusicBandCollection().toString());
        printer.println(SUCCESS_ADD);
        return new ClientResponse(printer.toString());
    }

}
