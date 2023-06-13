package ru.itmo.edu.sppo.lab6.database.service.service;

import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;

import java.sql.SQLException;

public interface UserSessionService {
    int getUserIdBySession(String session) throws SQLException, AuthorizationException;
}
