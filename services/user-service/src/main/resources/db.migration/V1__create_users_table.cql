CREATE TABLE IF NOT EXISTS users
(
    id                UUID PRIMARY KEY,
    username          VARCHAR,
    registration_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_username_to_id
(
    username VARCHAR PRIMARY KEY,
    id       UUID
)


