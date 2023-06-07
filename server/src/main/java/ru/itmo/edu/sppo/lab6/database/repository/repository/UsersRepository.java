package ru.itmo.edu.sppo.lab6.database.repository.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface UsersRepository {
    int addUserReturnId(Connection conn, String login, String password) throws SQLException;

    int getIdByLogin(Connection conn, String login) throws SQLException;
}
