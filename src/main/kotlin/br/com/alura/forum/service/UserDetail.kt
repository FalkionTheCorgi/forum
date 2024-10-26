package br.com.alura.forum.service

import br.com.alura.forum.model.User
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
    private val user: User
): UserDetails {
    override fun getAuthorities() = user.role

    override fun getPassword() = user.password

    override fun getUsername() = user.email
}