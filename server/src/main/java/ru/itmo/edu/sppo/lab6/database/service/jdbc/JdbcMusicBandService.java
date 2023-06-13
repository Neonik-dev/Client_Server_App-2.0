package ru.itmo.edu.sppo.lab6.database.service.jdbc;

import ru.itmo.edu.sppo.lab6.database.DataSource;
import ru.itmo.edu.sppo.lab6.database.repository.repository.MusicBandRepository;
import ru.itmo.edu.sppo.lab6.database.service.service.MusicBandService;
import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcMusicBandService implements MusicBandService {
    private final MusicBandRepository musicBandRepository;

    public JdbcMusicBandService(MusicBandRepository musicBandRepository) {
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public MusicBand add(MusicBand musicBand, int userId) throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            int musicBandId = musicBandRepository.add(conn, musicBand, userId);
            MusicBand musicBandFromDB = musicBandRepository.getMusicBandById(conn, musicBandId);
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

    @Override
    public int deleteByIdAndUserId(int musicBandId, int userId) throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            return musicBandRepository.deleteByIdAndUserId(conn, musicBandId, userId);
        }
    }

    @Override
    public MusicBand deleteAndReturnHeadByUserId(int userId) throws SQLException, IncorrectDataEntryExceptions {
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            MusicBand musicBand = musicBandRepository.getHeadMusicBandByUserId(conn, userId);
            musicBandRepository.deleteByIdAndUserId(conn, musicBand.getId(), userId);
            conn.commit();
            return musicBand;
        }
    }

    @Override
    public ArrayList<Integer> deleteByUserId(int userId) throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            return musicBandRepository.deleteByUserId(conn, userId);
        }
    }

    @Override
    public MusicBand getMusicBandByUserIdAndId(int userId, int musicBandId) throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            return musicBandRepository.getMusicBandByUserIdAndId(conn, userId, musicBandId);
        }
    }

    @Override
    public void updateByUserId(MusicBand musicBand, int userId) throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            musicBandRepository.updateByUserId(conn, musicBand, userId);
        }
    }
}
