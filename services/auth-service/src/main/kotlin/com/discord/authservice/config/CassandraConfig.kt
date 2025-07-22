package com.discord.authservice.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class CassandraConfig {
    @Bean
    fun localDcCustomizer(): CqlSessionBuilderCustomizer =
        CqlSessionBuilderCustomizer { builder ->
            builder.withLocalDatacenter("datacenter1")
                .withKeyspace("auth_keyspace")
        }

    @Bean
    @ConfigurationProperties(prefix = "spring.flyway")
    fun flywayProperties(): FlywayProperties = FlywayProperties()

    @Bean
    fun cassandraDataSource(flywayProperties: FlywayProperties): DataSource {
        val dataSource = DriverManagerDataSource()

        dataSource.setDriverClassName("com.ing.data.cassandra.jdbc.CassandraDriver")
        dataSource.url = flywayProperties.url

        return dataSource
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.flyway", name = ["enabled"], havingValue = "true", matchIfMissing = true)
    fun flyway(
        dataSource: DataSource,
        flywayProperties: FlywayProperties
    ): Flyway {
        val config = Flyway.configure()
            .dataSource(dataSource)
            .defaultSchema(flywayProperties.defaultSchema)
            .sqlMigrationSuffixes(*flywayProperties.sqlMigrationSuffixes.toTypedArray())
            .baselineOnMigrate(flywayProperties.isBaselineOnMigrate)
            .locations(*flywayProperties.locations.toTypedArray())

        return config.load().also { it.migrate() }
    }
}
