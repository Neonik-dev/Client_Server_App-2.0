--liquibase formatted sql

--changeset g.liseychikov:8_delete_default_in_establishment_date_in_music_band
ALTER TABLE music_band ALTER COLUMN establishment_date DROP DEFAULT;