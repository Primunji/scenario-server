package dev.euns.fishingstopserver.domain.user.service


import dev.euns.fishingstopserver.domain.user.exception.UserErrorCode
import dev.euns.fishingstopserver.domain.user.repository.UserRepository
import dev.euns.fishingstopserver.global.exception.CustomException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username).orElseThrow { CustomException(UserErrorCode.USER_NOT_FOUND) }
        return org.springframework.security.core.userdetails.User(user.username, user.password, emptyList())
    }
}