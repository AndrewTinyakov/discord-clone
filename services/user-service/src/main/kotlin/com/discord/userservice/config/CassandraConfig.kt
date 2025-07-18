package com.discord.userservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@Configuration
@EnableCassandraRepositories(basePackages = ["com.discord.userservice.repository"])
class CassandraConfig