package br.com.alura.forum.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        try {
            http
                .authorizeHttpRequests {
                    it.requestMatchers("/topics").hasAuthority("READ_WRITE").anyRequest().authenticated()
                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }.formLogin{
                    it.disable()
                }.httpBasic(Customizer.withDefaults())
            return http.build()
        } catch (e: Exception){
            throw(e)
        }
    }

    @Bean
    fun authConfigure(): DaoAuthenticationProvider {
        try {
            val authProvider = DaoAuthenticationProvider()
            authProvider.setUserDetailsService(
                userDetailsService
            )
            authProvider.setPasswordEncoder(
                bCryptPasswordEncoder()
            )
            return authProvider
        } catch (e: Exception){
            throw (e)
        }
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}