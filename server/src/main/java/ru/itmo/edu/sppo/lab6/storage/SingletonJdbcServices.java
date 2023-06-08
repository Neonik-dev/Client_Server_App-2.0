package ru.itmo.edu.sppo.lab6.storage;

import lombok.Getter;
import ru.itmo.edu.sppo.lab6.database.repository.jdbc.JdbcMusicBandRepository;
import ru.itmo.edu.sppo.lab6.database.repository.jdbc.JdbcUserSessionRepository;
import ru.itmo.edu.sppo.lab6.database.service.jdbc.JdbcMusicBandService;
import ru.itmo.edu.sppo.lab6.database.service.jdbc.JdbcUserSessionService;
import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.database.service.service.UserSessionService;

public class SingletonJdbcServices {
    @Getter
    private static final MusicBandService MUSIC_BAND_SERVICE = new JdbcMusicBandService(new JdbcMusicBandRepository());
    @Getter
    private static final UserSessionService USER_SESSION_SERVICE = new JdbcUserSessionService(
            new JdbcUserSessionRepository()
    );
}
