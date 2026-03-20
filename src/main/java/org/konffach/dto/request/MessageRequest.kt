package org.konffach.dto.request

data class MessageRequest(
    val content: String,
    val type: String,
    val replyTo: String?
)