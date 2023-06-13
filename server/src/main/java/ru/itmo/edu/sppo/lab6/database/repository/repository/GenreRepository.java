package ru.itmo.edu.sppo.lab6.database.repository.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface GenreRepository {
    String getNameById(Connection conn, int genreId) throws SQLException;

    int getIdByName(Connection conn, String genreName) throws SQLException;
}
