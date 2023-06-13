--liquibase formatted sql

--changeset g.liseychikov:6_fill_in_genre_table
INSERT INTO genre(name) VALUES ('PROGRESSIVE_ROCK') ON CONFLICT (name) DO NOTHING;
INSERT INTO genre(name) VALUES ('RAP') ON CONFLICT (name) DO NOTHING;
INSERT INTO genre(name) VALUES ('BLUES') ON CONFLICT (name) DO NOTHING;
INSERT INTO genre(name) VALUES ('PUNK_ROCK') ON CONFLICT (name) DO NOTHING;
