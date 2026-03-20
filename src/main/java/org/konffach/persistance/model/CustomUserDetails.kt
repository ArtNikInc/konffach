package org.konffach.persistance.model

import konffach.generated.jooq.package_.tables.records.UsersRecord
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    val user: UsersRecord
) : UserDetails {

    override fun getUsername(): String = user.login
    override fun getPassword(): String = ""

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(
        SimpleGrantedAuthority("ROLE_USER"))
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}