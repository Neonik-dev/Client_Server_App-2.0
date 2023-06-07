package ru.itmo.edu.sppo.lab6.database.service.service;

import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;

import java.sql.SQLException;

public interface UsersService {
    void register(String login, String password, String session) throws SQLException;

    void authorization(String login, String password, String session) throws AuthorizationException, SQLException;
}
