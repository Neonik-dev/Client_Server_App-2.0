package ru.itmo.edu.sppo.lab6.database.service.jdbc;

import ru.itmo.edu.sppo.lab6.database.DataSource;
import ru.itmo.edu.sppo.lab6.database.repository.repository.MusicBandRepository;
import ru.itmo.edu.sppo.lab6.database.repository.repository.UsersRepository;
import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcMusicBandService implements MusicBandService {
    private final UsersRepository usersRepository;
    private final MusicBandRepository musicBandRepository;

    public JdbcMusicBandService(UsersRepository usersRepository, MusicBandRepository musicBandRepository) {
        this.usersRepository = usersRepository;
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public MusicBand add(MusicBand musicBand) throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            int userId = musicBandRepository.add(conn, musicBand);
            MusicBand musicBandFromDB = musicBandRepository.getMusicBandById(conn, userId);
            conn.commit();
            return musicBandFromDB;
        }
    }

    @Override
    public ArrayList<MusicBand> getAll() throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            return musicBandRepository.getAll(conn);
        }
    }
}
