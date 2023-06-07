package ru.itmo.edu.sppo.lab6.database.service.jdbc;

import ru.itmo.edu.sppo.lab6.database.DataSource;
import ru.itmo.edu.sppo.lab6.database.repository.repository.UserSessionRepository;
import ru.itmo.edu.sppo.lab6.database.repository.repository.UsersRepository;
import ru.itmo.edu.sppo.lab6.database.service.service.UsersService;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUsersService implements UsersService {
    private final UsersRepository usersRepository;
    private final UserSessionRepository userSessionRepository;

    public JdbcUsersService(UsersRepository usersRepository, UserSessionRepository userSessionRepository) {
        this.usersRepository = usersRepository;
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    public void register(String login, String password, String session) throws SQLException {
        try (Connection conn = DataSource.getConnection()) {
            conn.setAutoCommit(false);
            int userId = usersRepository.addUserReturnId(conn, login, password);
            userSessionRepository.deleteByUserId(conn, userId);
            userSessionRepository.add(conn, session, userId);
            conn.commit();
        }
    }

    @Override
    public void authorization(String login, String password) {
    }
}
