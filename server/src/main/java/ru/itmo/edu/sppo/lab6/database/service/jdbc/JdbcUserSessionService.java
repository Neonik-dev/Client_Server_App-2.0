package ru.itmo.edu.sppo.lab6.database.service.jdbc;

import ru.itmo.edu.sppo.lab6.database.DataSource;
import ru.itmo.edu.sppo.lab6.database.repository.repository.UserSessionRepository;
import ru.itmo.edu.sppo.lab6.database.service.service.UserSessionService;
import ru.itmo.edu.sppo.lab6.exceptions.AuthorizationException;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUserSessionService implements UserSessionService {
    private final UserSessionRepository userSessionRepository;

    public JdbcUserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public int getUserIdBySession(String session) throws SQLException, AuthorizationException {
        try (Connection conn = DataSource.getConnection()) {
            return userSessionRepository.getUserIdBySession(conn, session);
        }
    }
}
