package com.discord.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*


@Component
class CurrentUserContextInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val userId = request.getHeader("X-User-Id")?.let {
            UUID.fromString(it)
        }
        val roles = request.getHeader("X-User-Roles")?.let { header ->
            listOf(
                *header.split(",".toRegex())
                    .dropLastWhile {
                        it.isEmpty()
                    }.toTypedArray()
            )
        }

        if (userId != null && roles != null) {
            CurrentUserContextHolder.set(CurrentUser(userId, roles))
        }

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        CurrentUserContextHolder.clear()
    }
}