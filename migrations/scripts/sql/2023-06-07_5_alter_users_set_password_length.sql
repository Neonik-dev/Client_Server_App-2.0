--liquibase formatted sql

--changeset g.liseychikov:5_alter_users_set_password_length
ALTER TABLE users ALTER COLUMN password TYPE varchar(96);
