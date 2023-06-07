package ru.itmo.edu.sppo.lab6.database.repository.repository;

import ru.itmo.edu.sppo.lab6.dto.collectionItem.MusicBand;

import java.sql.Connection;
import java.sql.SQLException;

public interface MusicBandRepository {
    int add(Connection conn, MusicBand musicBand, int genreId) throws SQLException;

    MusicBand getMusicBandById(Connection conn, int musicBandId) throws SQLException;
}
