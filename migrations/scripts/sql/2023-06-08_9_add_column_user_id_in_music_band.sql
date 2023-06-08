--liquibase formatted sql

--changeset g.liseychikov:9_add_column_user_id_in_music_band
ALTER TABLE music_band ADD COLUMN
    user_id     INTEGER     REFERENCES users(id)    NOT NULL;
