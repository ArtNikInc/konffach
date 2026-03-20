package org.konffach.controller.chat

import org.konffach.dto.response.MessageResponse
import org.konffach.service.chat.ChatService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(private val chatService: ChatService) {

    @PostMapping("/history")
    fun getChatHistory(): List<MessageResponse> = chatService.getHistory()
}