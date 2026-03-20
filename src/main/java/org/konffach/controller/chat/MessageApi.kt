package org.konffach.controller.chat

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.konffach.dto.request.MessageRequest
import org.konffach.persistance.model.CustomUserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal

@Tag(name = "Messages", description = "API for sending and managing chat messages")
@SecurityRequirement(name = "bearerAuth")
interface MessageApi {

    @Operation(
        summary = "Send a new message",
        description = "Sends a new message to the chat. Requires authentication with JWT token."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Message successfully sent",
                content = [Content(mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Validation error - Invalid message data",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        name = "Validation Error",
                        value = """
                            {
                                "status": 400,
                                "message": "Validation failed",
                                "errors": {
                                    "content": "Message content cannot be empty",
                                    "type": "Message type must be one of: TEXT, IMAGE, FILE"
                                }
                            }
                        """
                    )]
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized - Invalid or missing JWT token",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        name = "Unauthorized Error",
                        value = """
                            {
                                "status": 401,
                                "message": "Authentication required"
                            }
                        """
                    )]
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden - User doesn't have permission to send messages",
                content = [Content(mediaType = "application/json")]
            )
        ]
    )
    @RequestBody(
        description = "Message to be sent",
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = MessageRequest::class),
            examples = [ExampleObject(
                name = "Text Message Example",
                value = """
                    {
                        "content": "Hello everyone!",
                        "type": "TEXT",
                        "replyTo": null
                    }
                """
            ), ExampleObject(
                name = "Reply Message Example",
                value = """
                    {
                        "content": "Thanks for the info!",
                        "type": "TEXT",
                        "replyTo": "123e4567-e89b-12d3-a456-426614174000"
                    }
                """
            ), ExampleObject(
                name = "Image Message Example",
                value = """
                    {
                        "content": "https://example.com/image.jpg",
                        "type": "IMAGE",
                        "replyTo": null
                    }
                """
            )]
        )]
    )
    fun sendMessage(
        @Parameter(hidden = true)
        message: MessageRequest,

        @Parameter(
            description = "Authenticated user details (automatically injected by Spring Security)",
            hidden = true
        )
        @AuthenticationPrincipal userDetails: CustomUserDetails
    )
}