--liquibase formatted sql

--changeset g.liseychikov:3_create_music_band_table
CREATE TABLE IF NOT EXISTS music_band
(
    id                          SERIAL                          PRIMARY KEY,
    name                        TEXT                            UNIQUE      CHECK(name != '')           NOT NULL,
    coordinate_x                DOUBLE PRECISION                                                        NOT NULL,
    coordinate_y                BIGINT                                                                  NOT NULL,
    creation_date               TIMESTAMP WITHOUT TIME ZONE     default now()                           NOT NULL,
    number_of_participants      BIGINT                          CHECK(number_of_participants > 0),
    description                 TEXT                                                                    NOT NULL,
    establishment_date          TIMESTAMP WITHOUT TIME ZONE     default now(),
    genre_id                    INTEGER                         REFERENCES genre(id)                    NOT NULL,
    studio_id                   SERIAL                                                                  NOT NULL,
    studio_address              TEXT
);
