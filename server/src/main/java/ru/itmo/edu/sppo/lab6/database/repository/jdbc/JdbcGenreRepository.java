package ru.itmo.edu.sppo.lab6.database.repository.jdbc;

import ru.itmo.edu.sppo.lab6.database.repository.repository.GenreRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcGenreRepository implements GenreRepository {
    private static final String SELECT_NAME_BY_ID_QUERY = "SELECT name FROM genre WHERE id=?";
    private static final String SELECT_ID_BY_NAME_QUERY = "SELECT id FROM genre WHERE name=?";

    @Override
    public String getNameById(Connection conn, int genreId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(SELECT_NAME_BY_ID_QUERY)) {
            statement.setInt(1, genreId);
            statement.executeQuery();

            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return result.getString("name");
            }
        }
        throw new SQLException(String.format("В таблице genre отсутсвует жанр с (id)=(%d)", genreId));
    }

    @Override
    public int getIdByName(Connection conn, String genreName) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(SELECT_ID_BY_NAME_QUERY)) {
            statement.setString(1, genreName);
            statement.executeQuery();

            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return result.getInt("id");
            }
        }
        throw new SQLException(String.format("В таблице genre отсутсвует жанр с (name)=(%s)", genreName));
    }
}
