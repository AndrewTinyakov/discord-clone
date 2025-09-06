package com.discord.chatservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<Map<String, String>> {
        val body = mapOf(
            "error" to "Not Found",
            "message" to (e.message ?: "Resource not found")
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

}