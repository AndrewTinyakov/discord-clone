package com.discord.authservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories(basePackages = ["com.discord.authservice.repository"])
class CassandraConfig : AbstractCassandraConfiguration() {
    override fun getKeyspaceName(): String = "auth_keyspace"
    override fun getContactPoints(): String = "cassandra"
    override fun getPort(): Int = 9042
}