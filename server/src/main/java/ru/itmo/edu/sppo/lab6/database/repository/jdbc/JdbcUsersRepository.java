package ru.itmo.edu.sppo.lab6.database.repository.jdbc;

import ru.itmo.edu.sppo.lab6.database.repository.repository.UsersRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUsersRepository implements UsersRepository {
    private static final String INSERT_QUERY = "INSERT INTO users(login, password) VALUES(?, ?)";
    private static final String SELECT_ID_BY_LOGIN_QUERY = "SELECT id FROM users WHERE login=?";

    @Override
    public int addUserReturnId(Connection conn, String login, String password) throws SQLException {
        int user_id = 0;
        try (PreparedStatement statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user_id = resultSet.getInt(1);
        }
        return user_id;
    }

    @Override
    public int getIdByLogin(Connection conn, String login) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(SELECT_ID_BY_LOGIN_QUERY)) {
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt(1);
        }
    }
}
