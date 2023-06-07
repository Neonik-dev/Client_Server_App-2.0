--liquibase formatted sql

--changeset g.liseychikov:1_create_users_table
CREATE TABLE IF NOT EXISTS users
(
    id                  SERIAL                          PRIMARY KEY,
    login               VARCHAR(255)                    UNIQUE              NOT NULL,
    password            VARCHAR(255)                                        NOT NULL,
    registration_date   TIMESTAMP WITHOUT TIME ZONE     default now()       NOT NULL
);
