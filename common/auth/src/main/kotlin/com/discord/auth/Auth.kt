package com.discord.auth

import java.util.*


object Auth {
    fun userId(): UUID {
        return CurrentUserContextHolder.getRequired().id
    }

    fun roles() = CurrentUserContextHolder.getRequired().roles

    fun hasRole(role: String?): Boolean {
        return CurrentUserContextHolder.getRequired().roles.contains(role)
    }
}