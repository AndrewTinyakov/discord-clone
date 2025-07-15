package com.discord.events.core

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    fun publish(topic: String, event: DomainEvent) {
        val json = objectMapper.writeValueAsString(event)
        kafkaTemplate.send(topic, event.eventId.toString(), json)
    }
}