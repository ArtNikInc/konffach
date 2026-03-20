package org.konffach.dto.response

import java.util.UUID

data class MessageResponse(
    val id: UUID,
    val content: String,
    val type: String,
    val user: String,
    val replyTo: UUID?
)