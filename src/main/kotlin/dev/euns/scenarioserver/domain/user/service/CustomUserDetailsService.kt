package dev.euns.scenarioserver.domain.user.service


import dev.euns.scenarioserver.domain.user.entity.UserEntity
import dev.euns.scenarioserver.domain.user.exception.UserErrorCode
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import dev.euns.scenarioserver.global.exception.CustomException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.security.Principal
import kotlin.jvm.optionals.toList

@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username).orElseThrow { CustomException(UserErrorCode.USER_NOT_FOUND) }
        return org.springframework.security.core.userdetails.User(user.username, user.password, emptyList())
    }
    fun getUser(
        principal: Principal
    ): BaseResponse<List<UserEntity>> {
        val user = userRepository.findByUsername(principal.name).toList()
        return BaseResponse(status = 200, data = user, message = "유저를 불러왔습니다.")
    }
}