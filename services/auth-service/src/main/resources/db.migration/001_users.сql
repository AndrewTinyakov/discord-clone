CREATE KEYSPACE IF NOT EXISTS auth_keyspace
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 3
        };

USE auth_keyspace;

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
