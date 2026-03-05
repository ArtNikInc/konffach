-- liquibase formatted sql

-- changeset author:lranger
CREATE TABLE users (
    id UUID NOT NULL,
    login VARCHAR(12) NOT NULL UNIQUE,
    password   VARCHAR(256) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_users PRIMARY KEY (id)
);