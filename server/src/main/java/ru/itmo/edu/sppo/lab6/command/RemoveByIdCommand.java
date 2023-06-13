package ru.itmo.edu.sppo.lab6.command;

import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.database.service.service.UserSessionService;
import ru.itmo.edu.sppo.lab6.dto.ClientRequest;
import ru.itmo.edu.sppo.lab6.dto.ClientResponse;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;
import ru.itmo.edu.sppo.lab6.storage.SingletonJdbcServices;
import ru.itmo.edu.sppo.lab6.utils.CheckID;
import ru.itmo.edu.sppo.lab6.utils.CheckNumberOfArguments;
import ru.itmo.edu.sppo.lab6.utils.Printer;

import java.sql.SQLException;

public class RemoveByIdCommand implements BaseCommand {
    private static final String NAME = "remove_by_id";
    private static final String SUCCESS = "Удаление элемента с (id)=(%d) прошло успешно";
    private static final String TEXT_CHECK_ARGUMENTS = "Необходимо ввести один числовой аргумент -> id";
    private static final MusicBandService MUSIC_BAND_SERVICE = SingletonJdbcServices.getMUSIC_BAND_SERVICE();
    private static final UserSessionService USER_SESSION_SERVICE = SingletonJdbcServices.getUSER_SESSION_SERVICE();

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandDescription() {
        return NAME + " id ->  удаляет элемент из коллекции по его id";
    }

    @Override
    public ClientResponse execute(ClientRequest request, Printer printer)
            throws IncorrectDataEntryExceptions, SQLException {
        checkArgs(request.getArgument());
        int musicBandId = Integer.parseInt(request.getArgument()[0]);
        int userId =  USER_SESSION_SERVICE.getUserIdBySession(request.getSession());

        if (MUSIC_BAND_SERVICE.deleteByIdAndUserId(musicBandId, userId) == 0) {
            throw new IncorrectDataEntryExceptions(
                    String.format(
                            "К сожалению, не вы создавали запись с (id)=(%d), поэтому нельзя удалить эту запись",
                            musicBandId
                    )
            );
        }
        MusicBandCollection.delete(musicBandId);

        printer.println(String.format(SUCCESS, musicBandId));
        return new ClientResponse(printer.toString());
    }

    @Override
    public void checkArgs(String[] args) throws IncorrectDataEntryExceptions {
        CheckNumberOfArguments.check(args, 1, TEXT_CHECK_ARGUMENTS);
        CheckID.checkExistsID(args[0]);
    }
}
