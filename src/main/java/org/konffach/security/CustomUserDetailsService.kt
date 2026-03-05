package org.konffach.security

import org.konffach.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {
    
    override fun loadUserByUsername(username: String): UserDetails {
        // Поиск по username или email
        val user = userService.findByLogin(username)
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.login)
            .password(user.password)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }
}