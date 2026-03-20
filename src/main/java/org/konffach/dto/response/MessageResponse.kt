package org.konffach.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(
    description = "Message response DTO containing complete message information",
    example = """
        {
            "id": "123e4567-e89b-12d3-a456-426614174000",
            "content": "Hello everyone!",
            "type": "TEXT",
            "user": "john_doe",
            "replyTo": null
        }
    """
)
data class MessageResponse(
    @param:Schema(
        description = "Unique identifier of the message",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    val id: UUID,

    @param:Schema(
        description = "Content of the message",
        example = "Hello everyone!",
        required = true
    )
    val content: String,

    @param:Schema(
        description = "Type of the message",
        example = "TEXT",
        allowableValues = ["TEXT", "IMAGE", "FILE", "VIDEO", "AUDIO"],
        required = true
    )
    val type: String,

    @param:Schema(
        description = "Username of the message sender",
        example = "john_doe",
        required = true
    )
    val user: String,

    @param:Schema(
        description = "ID of the message being replied to (null if not a reply)",
        example = "123e4567-e89b-12d3-a456-426614174000",
        nullable = true
    )
    val replyTo: UUID?
)