package com.discord.events.ws

import com.discord.events.core.DomainEvent
import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant
import java.util.UUID

data class WsPublishEvent(
    val channels: List<String>,
    val payload: JsonNode,
    val event: String,
    override val eventId: UUID = UUID.randomUUID(),
    override val occurredAt: Instant = Instant.now()
) : DomainEvent
