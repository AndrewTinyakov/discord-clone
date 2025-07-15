package com.discord.auth

import java.util.UUID

data class CurrentUser(
    val id: UUID,
    val roles: List<String>
)