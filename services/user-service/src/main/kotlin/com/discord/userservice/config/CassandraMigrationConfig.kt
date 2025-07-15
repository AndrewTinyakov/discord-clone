package com.discord.userservice.config

import com.datastax.oss.driver.api.core.CqlSession
import org.cognitor.cassandra.migration.Database
import org.cognitor.cassandra.migration.MigrationConfiguration
import org.cognitor.cassandra.migration.MigrationRepository
import org.cognitor.cassandra.migration.MigrationTask
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CassandraMigrationConfig {
    @Bean
    fun migrationConfig(): MigrationConfiguration =
        MigrationConfiguration().withKeyspaceName("user_keyspace")

    @Bean
    fun migrationTask(
        session: CqlSession,
        config: MigrationConfiguration
    ): MigrationTask {
        val database = Database(session, config)
        val repository = MigrationRepository("db/migration")

        val task = MigrationTask(database, repository)

        task.migrate()
        return task
    }
}