package ru.itmo.edu.sppo.lab6.database.repository.jdbc;

import ru.itmo.edu.sppo.lab6.database.repository.repository.UserSessionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUserSessionRepository implements UserSessionRepository {
    private static final String INSERT_QUERY = "INSERT INTO user_session(session, user_id) VALUES (?, ?)";
    private static final String DELETE_BY_USER_ID_QUERY = "DELETE FROM user_session WHERE user_id = ?";

    @Override
    public void add(Connection conn, String session, int userId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, session);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteByUserId(Connection conn, int userId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_BY_USER_ID_QUERY)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}
