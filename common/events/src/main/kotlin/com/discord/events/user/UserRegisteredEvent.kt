package com.discord.events.user

import com.discord.events.core.DomainEvent
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

data class UserRegisteredEvent(
    val id: UUID,
    val username: String,
    val registeredAt: OffsetDateTime,
    override val eventId: UUID = UUID.randomUUID(),
    override val occurredAt: Instant = Instant.now()
): DomainEvent