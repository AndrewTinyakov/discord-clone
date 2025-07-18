CREATE KEYSPACE IF NOT EXISTS discord_clone
    WITH replication = {
        'class':'SimpleStrategy',
        'replication_factor':3
        };

CREATE ROLE IF NOT EXISTS app_user
    WITH PASSWORD = 'cassandra'
     AND LOGIN = true;

GRANT ALL PERMISSIONS ON KEYSPACE discord_clone TO app_user;