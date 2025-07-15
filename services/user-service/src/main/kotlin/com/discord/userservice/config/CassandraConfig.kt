package com.discord.userservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories(basePackages = ["com.discord.userservice.repository"])
class CassandraConfig : AbstractCassandraConfiguration() {
    override fun getKeyspaceName(): String = "user_keyspace"
    override fun getContactPoints(): String = "cassandra"
    override fun getPort(): Int = 9042
}