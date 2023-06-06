--liquibase formatted sql

--changeset g.liseychikov:2_create_genre_table
CREATE TABLE IF NOT EXISTS genre
(
    id          SERIAL              PRIMARY KEY,
    name        VARCHAR(255)        UNIQUE          NOT NULL
);
