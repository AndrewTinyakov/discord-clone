package com.discord.clients.user

import com.discord.dto.user.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@FeignClient(name = "user-service")
interface UserServiceClient {

    @GetMapping("/api/users")
    fun getByIds(@RequestParam ids: Set<UUID>): List<UserResponse>

}