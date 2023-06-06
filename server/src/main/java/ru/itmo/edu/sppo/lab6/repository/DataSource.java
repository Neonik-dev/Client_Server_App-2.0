package ru.itmo.edu.sppo.lab6.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DataSource {
    private static final String NAME_PROPERTIES = "database.properties";
    private static final HikariDataSource DATA_SOURCE;

    static {
        HikariConfig config = new HikariConfig(readProperties());
        DATA_SOURCE = new HikariDataSource(config);
    }

    private static Properties readProperties() {
        try {
            Properties properties = new Properties();
            properties.load(
                    DataSource.class.getClassLoader().getResourceAsStream(NAME_PROPERTIES)
            );
            return properties;
        } catch (IOException e) {
            log.error("Проблема с файлом database.properties");
            throw new RuntimeException(e);
        }
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
