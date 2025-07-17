package com.discord.authservice.config

import com.datastax.oss.driver.api.core.CqlSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories
import java.net.InetSocketAddress

@Configuration
@EnableReactiveCassandraRepositories(basePackages = ["com.discord.authservice.repository"])
class CassandraConfig : AbstractCassandraConfiguration() {
    override fun getKeyspaceName(): String = "auth_keyspace"
    override fun getContactPoints(): String = "cassandra"
    override fun getPort(): Int = 9042
    override fun getLocalDataCenter(): String = "datacenter1"

    @Bean
    @Primary
    fun sessionBuilder(): CqlSession {
        val bootstrap = CqlSession.builder()
            .addContactPoint(InetSocketAddress("cassandra", 9042))
            .withLocalDatacenter("datacenter1")
            .build()

        bootstrap.execute(
            """
            CREATE KEYSPACE IF NOT EXISTS auth_keyspace
            WITH replication = {'class':'SimpleStrategy','replication_factor':1};
            """.trimIndent()
        )
        bootstrap.close()

        return CqlSession.builder()
            .addContactPoint(InetSocketAddress("cassandra", 9042))
            .withLocalDatacenter("datacenter1")
            .withKeyspace("auth_keyspace")
            .build()
    }
}