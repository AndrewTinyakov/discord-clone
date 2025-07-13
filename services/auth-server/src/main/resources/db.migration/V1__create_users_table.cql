CREATE TABLE IF NOT EXISTS users (
    id uuid PRIMARY KEY,
    username text,
    password text,
    roles set<text>
);

CREATE INDEX IF NOT EXISTS users_username_idx ON users (username);