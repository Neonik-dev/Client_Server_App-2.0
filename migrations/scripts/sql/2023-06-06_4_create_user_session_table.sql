--liquibase formatted sql

--changeset g.liseychikov:4_create_user_session_table
CREATE TABLE IF NOT EXISTS user_session
(
    id              SERIAL                              PRIMARY KEY,
    user_id         INTEGER                             REFERENCES users(id)    UNIQUE         NOT NULL,
    session         VARCHAR(35)                         UNIQUE                                 NOT NULL,
    creation_date   TIMESTAMP WITHOUT TIME ZONE         UNIQUE     default now()               NOT NULL
);
