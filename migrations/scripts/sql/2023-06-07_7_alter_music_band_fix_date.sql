--liquibase formatted sql

--changeset g.liseychikov:7_alter_music_band_fix_date
ALTER TABLE music_band ALTER COLUMN creation_date TYPE DATE;
ALTER TABLE music_band ALTER COLUMN establishment_date TYPE DATE;
