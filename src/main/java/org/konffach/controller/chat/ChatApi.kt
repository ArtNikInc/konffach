package org.konffach.controller.chat

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.konffach.dto.response.MessageResponse

@Tag(name = "Chat History", description = "API for retrieving chat history and messages")
@SecurityRequirement(name = "bearerAuth")
interface ChatApi {

    @Operation(
        summary = "Get chat history",
        description = "Retrieves the complete chat history with all messages. Returns messages in chronological order."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Chat history successfully retrieved",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(
                        schema = Schema(implementation = MessageResponse::class)
                    ),
                    examples = [ExampleObject(
                        name = "Success Response Example",
                        value = """
                            [
                                {
                                    "id": "123e4567-e89b-12d3-a456-426614174000",
                                    "content": "Hello everyone!",
                                    "type": "TEXT",
                                    "user": "john_doe",
                                    "replyTo": null
                                },
                                {
                                    "id": "123e4567-e89b-12d3-a456-426614174001",
                                    "content": "Hi John!",
                                    "type": "TEXT",
                                    "user": "jane_smith",
                                    "replyTo": null
                                },
                                {
                                    "id": "123e4567-e89b-12d3-a456-426614174002",
                                    "content": "Thanks for the info!",
                                    "type": "TEXT",
                                    "user": "john_doe",
                                    "replyTo": "123e4567-e89b-12d3-a456-426614174001"
                                }
                            ]
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
                description = "Forbidden - User doesn't have permission to view chat history",
                content = [Content(mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [Content(mediaType = "application/json")]
            )
        ]
    )
    fun getChatHistory(): List<MessageResponse>
}