package org.konffach.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.konffach.dto.request.RefreshTokenRequest
import org.konffach.dto.request.UserRequest
import org.konffach.dto.response.JwtResponse
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "User Authentication", description = "API for user registration, authentication and token management")
interface UserApi {

    @Operation(
        summary = "Register new user",
        description = "Creates a new user account with the provided credentials. Returns JWT tokens for authentication."
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "User successfully registered", content = [Content(
                mediaType = "application/json", examples = [ExampleObject(
                    name = "Success Response", value = """
                            {
                                "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTM4MDAwfQ.signature",
                                "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTQxNjAwfQ.signature"
                            }
                        """
                )]
            )]
        ), ApiResponse(
            responseCode = "400", description = "Validation error - Invalid input data", content = [Content(
                mediaType = "application/json", examples = [ExampleObject(
                    name = "Validation Error", value = """
                            {
                                "status": 400,
                                "message": "Validation failed",
                                "errors": {
                                    "login": "Login must be between 3 and 50 characters",
                                    "password": "Password must contain at least one digit, one uppercase, one lowercase letter and one special character"
                                }
                            }
                        """
                )]
            )]
        ), ApiResponse(
            responseCode = "409", description = "User already exists with this login", content = [Content(
                mediaType = "application/json", examples = [ExampleObject(
                    name = "Conflict Error", value = """
                            {
                                "status": 409,
                                "message": "User with login 'john_doe' already exists"
                            }
                        """
                )]
            )]
        )]
    )
    @RequestBody(
        description = "User registration credentials", required = true, content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = UserRequest::class),
            examples = [ExampleObject(
                name = "Registration Example", value = """
                    {
                        "login": "john_doe",
                        "password": "SecurePass123!"
                    }
                """
            )]
        )]
    )
    fun register(
        @Parameter(hidden = true) request: UserRequest
    ): JwtResponse

    @Operation(
        summary = "Login user",
        description = "Authenticates user with login and password. Returns JWT tokens for authentication."
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "User successfully logged in", content = [Content(
                mediaType = "application/json", examples = [ExampleObject(
                    name = "Success Response", value = """
                            {
                                "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTM4MDAwfQ.signature",
                                "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTQxNjAwfQ.signature"
                            }
                        """
                )]
            )]
        ), ApiResponse(
            responseCode = "400",
            description = "Validation error - Invalid input data",
            content = [Content(mediaType = "application/json")]
        ), ApiResponse(
            responseCode = "401", description = "Invalid credentials - Wrong login or password", content = [Content(
                mediaType = "application/json", examples = [ExampleObject(
                    name = "Unauthorized Error", value = """
                            {
                                "status": 401,
                                "message": "Invalid login or password"
                            }
                        """
                )]
            )]
        )]
    )
    @RequestBody(
        description = "User login credentials", required = true, content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = UserRequest::class),
            examples = [ExampleObject(
                name = "Login Example", value = """
                    {
                        "login": "john_doe",
                        "password": "SecurePass123!"
                    }
                """
            )]
        )]
    )
    fun login(
        @Parameter(hidden = true) request: UserRequest
    ): JwtResponse

    @Operation(
        summary = "Refresh JWT token",
        description = "Refreshes access token using a valid refresh token. Returns new JWT tokens pair."
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "Tokens successfully refreshed", content = [Content(
                mediaType = "application/json", examples = [ExampleObject(
                    name = "Success Response", value = """
                            {
                                "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.new_access_token_signature",
                                "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.new_refresh_token_signature"
                            }
                        """
                )]
            )]
        ), ApiResponse(
            responseCode = "400",
            description = "Invalid request - Missing or malformed refresh token",
            content = [Content(mediaType = "application/json")]
        ), ApiResponse(
            responseCode = "401", description = "Invalid or expired refresh token", content = [Content(
                mediaType = "application/json", examples = [ExampleObject(
                    name = "Unauthorized Error", value = """
                            {
                                "status": 401,
                                "message": "Invalid or expired refresh token"
                            }
                        """
                )]
            )]
        )]
    )
    @RequestBody(
        description = "Refresh token request", required = true, content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = RefreshTokenRequest::class),
            examples = [ExampleObject(
                name = "Refresh Token Example", value = """
                    {
                        "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTQxNjAwfQ.signature"
                    }
                """
            )]
        )]
    )
    fun refresh(
        @Parameter(hidden = true) request: RefreshTokenRequest,

        @Parameter(
            description = "Current JWT access token (Bearer format)",
            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcxMDkzNDQwMCwiZXhwIjoxNzEwOTM4MDAwfQ.signature",
            required = true
        ) @RequestHeader("Authorization") jwtToken: String
    ): JwtResponse
}