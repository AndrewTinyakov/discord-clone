CREATE KEYSPACE IF NOT EXISTS chat_keyspace
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 3
        };

USE chat_keyspace;

CREATE TABLE IF NOT EXISTS chats
(
    id         VARCHAR PRIMARY KEY,
    name       VARCHAR,
    type       VARCHAR,
    created_at TIMESTAMP,
    members    SET<UUID>
);

CREATE TABLE chat_by_user_id
(
    user_id             UUID,
    chat_id             VARCHAR,
    groupChatName       VARCHAR,
    type                VARCHAR,
    last_message_at     TIMESTAMP,
    direct_recipient_id UUID,
    PRIMARY KEY (user_id, last_message_at, chat_id)
) WITH CLUSTERING ORDER BY (last_message_at DESC, chat_id ASC);




