package com.discord.pagination

data class CursorPage<C>(
    val content: List<C>,
    val cursor: String?
)