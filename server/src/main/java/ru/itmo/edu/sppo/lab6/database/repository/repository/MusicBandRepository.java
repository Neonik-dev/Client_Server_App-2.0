package ru.itmo.edu.sppo.lab6.database.repository.repository;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;
import ru.itmo.edu.sppo.lab6.exceptions.IncorrectDataEntryExceptions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MusicBandRepository {
    int add(Connection conn, MusicBand musicBand, int userId) throws SQLException;

    MusicBand getMusicBandById(Connection conn, int musicBandId) throws SQLException;

    ArrayList<MusicBand> getAll(Connection conn) throws SQLException;

    int deleteByIdAndUserId(Connection conn, int musicBandId, int userId) throws SQLException;

    MusicBand getHeadMusicBandByUserId(Connection conn, int userId) throws SQLException, IncorrectDataEntryExceptions;
}
