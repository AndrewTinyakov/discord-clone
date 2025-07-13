package com.discord.gateway.filter


import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import java.util.function.Predicate

@Component
class RouteValidator {

    var isSecured = Predicate { request: ServerHttpRequest ->
        openApiEndpoints
            .stream()
            .noneMatch { uri -> request.uri.getPath().contains(uri!!) }
    }

    companion object {
        val openApiEndpoints: MutableList<String?> = mutableListOf<String?>(
            "/auth/register",
            "/auth/login",
            "/auth/logout",
            "/eureka"
        )
    }
}