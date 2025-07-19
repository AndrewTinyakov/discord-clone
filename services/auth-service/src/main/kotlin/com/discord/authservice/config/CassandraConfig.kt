package com.discord.authservice.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class CassandraConfig {

    @Bean
    fun localDcCustomizer(): CqlSessionBuilderCustomizer =
        CqlSessionBuilderCustomizer { builder ->
            builder.withLocalDatacenter("datacenter1")
                .withKeyspace("auth_keyspace")
        }

    @Bean
    @ConditionalOnProperty(prefix = "flyway", name = ["enabled"], havingValue = "true", matchIfMissing = true)
    fun flyway(): Flyway? {
        val flyway =  Flyway.configure()
            .dataSource("jdbc:cassandra://localhost:9042?localdatacenter=datacenter1", null, null)
            .defaultSchema("auth_keyspace")
            .sqlMigrationSuffixes(".cql")
            .createSchemas(true)
            .baselineOnMigrate(true)
            .locations("classpath:db/migration")
            .load()
        flyway.migrate()

        return flyway
    }

    @Bean
    @ConfigurationProperties("spring.cassandra.jdbc")
    fun cassandraJdbcProperties(): Properties? {
        return Properties()
    }

}