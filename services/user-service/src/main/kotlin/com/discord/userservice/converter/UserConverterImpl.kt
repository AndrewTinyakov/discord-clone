package com.discord.userservice.converter

import com.discord.userservice.entity.User
import com.discord.userservice.payload.response.CurrentUserResponse
import com.discord.userservice.payload.response.UserResponse
import org.springframework.stereotype.Component

@Component
class UserConverterImpl : UserConverter {

    override fun convertToCurrentUser(
        user: User,
        roles: List<String>
    ): CurrentUserResponse = CurrentUserResponse(
        id = user.id.toString(),
        username = user.username,
        roles = roles
    )

    override fun convertToUser(user: User): UserResponse = UserResponse(
        id = user.id.toString(),
        username = user.username,
    )

}