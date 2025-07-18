package com.discord.authservice.config

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CassandraConfig {

    @Bean
    fun localDcCustomizer(): CqlSessionBuilderCustomizer =
        CqlSessionBuilderCustomizer { builder ->
            builder.withLocalDatacenter("datacenter1")
        }

}