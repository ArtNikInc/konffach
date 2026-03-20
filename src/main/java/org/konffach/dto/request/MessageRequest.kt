package org.konffach.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.util.UUID

@Schema(
    description = "Message request DTO for sending new messages",
    example = """
        {
            "content": "Hello everyone!",
            "type": "TEXT",
            "replyTo": null
        }
    """
)
data class MessageRequest(
    @get:NotBlank(message = "Message content cannot be empty")
    @get:Pattern(
        regexp = "^(?s).{1,5000}$",
        message = "Message content must be between 1 and 5000 characters"
    )
    @param:Schema(
        description = "Content of the message",
        example = "Hello everyone!",
        minLength = 1,
        maxLength = 5000,
        required = true
    )
    val content: String,

    @get:NotBlank(message = "Message type cannot be empty")
    @get:Pattern(
        regexp = "^(TEXT|IMAGE|FILE|VIDEO|AUDIO)$",
        message = "Message type must be one of: TEXT, IMAGE, FILE, VIDEO, AUDIO"
    )
    @param:Schema(
        description = "Type of the message",
        example = "TEXT",
        allowableValues = ["TEXT", "IMAGE", "FILE", "VIDEO", "AUDIO"],
        required = true
    )
    val type: String,

    @param:Schema(
        description = "ID of the message being replied to (optional)",
        example = "123e4567-e89b-12d3-a456-426614174000",
        nullable = true,
        required = false
    )
    val replyTo: UUID? = null
)