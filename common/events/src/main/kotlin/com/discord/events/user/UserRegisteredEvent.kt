package com.discord.events.user

import java.time.OffsetDateTime
import java.util.*

data class UserRegisteredEvent(
    val id: UUID,
    val username: String,
    val registeredAt: OffsetDateTime
)