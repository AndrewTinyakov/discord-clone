package com.discord.userservice.converter

import com.discord.userservice.entity.User
import com.discord.userservice.payload.response.CurrentUserResponse
import com.discord.userservice.payload.response.UserResponse

interface UserConverter {

    fun convertToCurrentUser(user: User, roles: List<String>): CurrentUserResponse

    fun convertToUser(user: User): UserResponse



}