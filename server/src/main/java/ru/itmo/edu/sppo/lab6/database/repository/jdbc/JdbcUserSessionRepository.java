package ru.itmo.edu.sppo.lab6.database.repository.jdbc;

import ru.itmo.edu.sppo.lab6.database.repository.repository.UserSessionRepository;
import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserSessionRepository implements UserSessionRepository {
    private static final String INSERT_QUERY = "INSERT INTO user_session(session, user_id) VALUES (?, ?)";
    private static final String DELETE_BY_USER_ID_QUERY = "DELETE FROM user_session WHERE user_id = ?";
    private static final String SELECT_USER_ID_BY_SESSION = "SELECT user_id FROM user_session WHERE session = ?";

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

    @Override
    public int getUserIdBySession(Connection conn, String session) throws SQLException, AuthorizationException {
        try (PreparedStatement statement = conn.prepareStatement(SELECT_USER_ID_BY_SESSION)) {
            statement.setString(1, session);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("user_id");
            }
        }
        throw new AuthorizationException("Сессия является недействительной. Необходимо снова авторизоваться");
    }
}
