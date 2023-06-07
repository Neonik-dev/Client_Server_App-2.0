package ru.itmo.edu.sppo.lab6.database.repository.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserSessionRepository {
    void add(Connection conn, String session, int userId) throws SQLException;

    void deleteByUserId(Connection conn, int userId) throws SQLException;
}
