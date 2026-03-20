package org.konffach.configuration

import io.github.oshai.kotlinlogging.KotlinLogging
import org.konffach.exception.LoginIncorrectException
import org.konffach.security.JwtService
import org.konffach.service.user.UserService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class WebSocketAuthInterceptor(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val userService: UserService
) : ChannelInterceptor {

    private val logger = KotlinLogging.logger {}

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)

        if (accessor != null) {
            val token = accessor.getFirstNativeHeader("Authorization")?.removePrefix("Bearer ")

            if (token != null) {
                try {
                    val username = jwtService.extractUsername(token)
                    val userDetails = userDetailsService.loadUserByUsername(username)

                    if (jwtService.validateToken(token, userDetails)) {
                        val auth = UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities
                        )
                        accessor.user = auth

                        accessor.sessionAttributes = mutableMapOf(
                            "user" to userService.findByLogin(username)
                        )
                    }
                } catch (e: Exception) {
                    logger.error { "WebSocket authentication failed: ${e.message}" }
                }
            }else {
                throw LoginIncorrectException("Token is missing")
            }
        }

        return message
    }
}