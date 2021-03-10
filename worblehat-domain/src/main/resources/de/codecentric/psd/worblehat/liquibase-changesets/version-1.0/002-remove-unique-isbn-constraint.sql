--liquibase formatted sql

--changeset betaworblers:drop_index
DROP INDEX isbn ON book;

--rollback CREATE INDEX isbn ON book;
