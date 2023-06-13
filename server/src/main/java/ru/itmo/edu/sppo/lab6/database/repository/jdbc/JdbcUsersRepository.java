package ru.itmo.edu.sppo.lab6.database.repository.jdbc;

import ru.itmo.edu.sppo.lab6.database.repository.repository.UsersRepository;
import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUsersRepository implements UsersRepository {
    private static final String INSERT_QUERY = "INSERT INTO users(login, password) VALUES(?, ?)";
    private static final String SELECT_ID_BY_LOGIN_AND_PASSWORD_QUERY =
            "SELECT id FROM users WHERE login=? AND password=?";

    @Override
    public int addUserReturnId(Connection conn, String login, String password) throws SQLException {
        try (var statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    @Override
    public int getIdByLoginAndPassword(Connection conn, String login, String password)
            throws SQLException, AuthorizationException {
        try (PreparedStatement statement = conn.prepareStatement(SELECT_ID_BY_LOGIN_AND_PASSWORD_QUERY)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        }
        throw new AuthorizationException("Неверно введен логин или пароль. Попробуйте снова.");
    }
}
