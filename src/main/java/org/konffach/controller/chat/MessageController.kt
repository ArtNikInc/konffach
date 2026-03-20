package org.konffach.controller.chat

import io.github.oshai.kotlinlogging.KotlinLogging
import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.konffach.dto.request.MessageRequest
import org.konffach.persistance.model.CustomUserDetails
import org.konffach.service.chat.ChatService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/messages")
class MessageController(
    private val chatService: ChatService
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("")
    fun sendMessage(
        @RequestBody message: MessageRequest,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ) {
        logger.info { "Received message: ${message}" }
        chatService.sendMessage(message, userDetails.user)
    }
}