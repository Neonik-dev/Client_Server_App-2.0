package ru.itmo.edu.sppo.lab6.document;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.edu.sppo.lab6.database.repository.jdbc.JdbcMusicBandRepository;
import ru.itmo.edu.sppo.lab6.database.repository.jdbc.JdbcUsersRepository;
import ru.itmo.edu.sppo.lab6.database.service.jdbc.JdbcMusicBandService;
import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.storage.MusicBandCollection;

import java.sql.SQLException;
import java.util.ArrayList;

@Slf4j
public class FillCollection {
    private static final MusicBandService musicBandService = new JdbcMusicBandService(
            new JdbcUsersRepository(), new JdbcMusicBandRepository()
    );

    private FillCollection() {
    }

    public static void fillFromDB() {
        ArrayList<MusicBand> allMusicBand;
        try {
            allMusicBand = musicBandService.getAll();
            log.debug("Вся коллекция успешно получена из базы данных");
        } catch (SQLException e) {
            log.error(
                    "Сервер не смог запуститься. Не удалось прочитать все элементы из коллекции по следующей причине: "
                            + e.getMessage()
            );
            throw new RuntimeException(e.getMessage());
        }

        MusicBandCollection.clear();
        for (MusicBand musicBand : allMusicBand) {
            MusicBandCollection.add(musicBand);
        }
        log.debug("Коллекция успешно очистилась и заполнилась из базы данных");
    }
}
