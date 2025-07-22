package com.discord.events.ws

import com.discord.events.core.DomainEvent
import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant
import java.time.OffsetDateTime
import java.util.UUID

data class WsMessageEvent(
    val userId: UUID,
    val event: String,
    val payload: JsonNode,
    override val eventId: UUID = UUID.randomUUID(),
    override val occurredAt: Instant = Instant.now()
) : DomainEvent