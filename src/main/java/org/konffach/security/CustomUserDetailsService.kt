package org.konffach.security

import org.konffach.persistance.model.CustomUserDetails
import org.konffach.service.user.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByLogin(username)

        return CustomUserDetails(user)
    }
}