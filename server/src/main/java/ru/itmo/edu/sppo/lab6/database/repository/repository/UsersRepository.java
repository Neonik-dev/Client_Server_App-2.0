package ru.itmo.edu.sppo.lab6.database.repository.repository;

import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;

import java.sql.Connection;
import java.sql.SQLException;

public interface UsersRepository {
    int addUserReturnId(Connection conn, String login, String password) throws SQLException;

    int getIdByLoginAndPassword(Connection conn, String login, String password)
            throws AuthorizationException, SQLException;
}
