PRAGMA journal_mode = WAL;
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS users
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    username   TEXT      NOT NULL UNIQUE,
    password   TEXT      NOT NULL,
    root       BOOLEAN   NOT NULL DEFAULT 0,
    perms      TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER IF NOT EXISTS update_users_timestamp
    AFTER UPDATE
    ON users
    FOR EACH ROW
BEGIN
    UPDATE users
    SET updated_at = CURRENT_TIMESTAMP
    WHERE id = OLD.id;
END;

CREATE TABLE IF NOT EXISTS groups
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    name       TEXT      NOT NULL UNIQUE,
    perms      TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER IF NOT EXISTS update_groups_timestamp
    AFTER UPDATE
    ON groups
    FOR EACH ROW
BEGIN
    UPDATE groups
    SET updated_at = CURRENT_TIMESTAMP
    WHERE id = OLD.id;
END;

CREATE TABLE IF NOT EXISTS u_groups
(
    uid INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    gid INTEGER NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
    PRIMARY KEY (uid, gid)
);

CREATE TABLE IF NOT EXISTS collections
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    uid        INTEGER   NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name       TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (uid, name)
);

CREATE TRIGGER IF NOT EXISTS update_collections_timestamp
    AFTER UPDATE
    ON collections
    FOR EACH ROW
BEGIN
    UPDATE collections
    SET updated_at = CURRENT_TIMESTAMP
    WHERE id = OLD.id;
END;

CREATE TABLE IF NOT EXISTS embeddings
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    cid           INTEGER   NOT NULL REFERENCES collections (id) ON DELETE CASCADE,
    content       TEXT      NOT NULL,
    content_hash  TEXT      NOT NULL,
    metadata      BLOB,
    metadata_hash TEXT      NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER IF NOT EXISTS update_embeddings_timestamp
    AFTER UPDATE
    ON embeddings
    FOR EACH ROW
BEGIN
    UPDATE embeddings
    SET updated_at = CURRENT_TIMESTAMP
    WHERE id = OLD.id;
END;