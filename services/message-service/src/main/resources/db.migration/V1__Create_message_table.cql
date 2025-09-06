CREATE KEYSPACE IF NOT EXISTS message_keyspace
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 3
        };

USE message_keyspace;




