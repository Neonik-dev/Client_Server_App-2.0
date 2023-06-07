package ru.itmo.edu.sppo.lab6.database;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.*;

@Slf4j
public class RunMigrations {
    private static final Path PROJECT_PATH = new File(".").toPath().toAbsolutePath().getParent();

    private RunMigrations() {
    }

    public static void run() {
        try (Connection conn = DataSource.getConnection();
             PostgresDatabase database = new PostgresDatabase()
        ) {
            database.setConnection(new JdbcConnection(conn));
            ResourceAccessor changelogDir = new DirectoryResourceAccessor(PROJECT_PATH.resolve("migrations"));
            Liquibase liquibase = new Liquibase("master.xml", changelogDir, database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            log.error("Миграции не смогли примениться. Сервер не будет запущен");
            throw new RuntimeException(e);
        }
    }
}
