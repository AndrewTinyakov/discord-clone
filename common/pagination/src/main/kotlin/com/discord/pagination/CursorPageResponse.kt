package com.discord.pagination

data class CursorPageResponse<C>(
    val content: List<C>,
    val cursor: String?
)
