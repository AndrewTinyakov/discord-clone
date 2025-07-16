CREATE TABLE IF NOT EXISTS users
(
    id       uuid PRIMARY KEY,
    username text,
    password text,
    roles    set<text>
);

CREATE TABLE IF NOT EXISTS user_username_to_id
(
    username VARCHAR PRIMARY KEY,
    id       UUID
)
