package dev.euns.scenarioserver.domain.auth.service

import dev.euns.scenarioserver.domain.auth.dto.request.AuthLoginRequest
import dev.euns.scenarioserver.domain.auth.dto.request.AuthRefreshRequest
import dev.euns.scenarioserver.domain.auth.dto.request.AuthSignupRequest
import dev.euns.scenarioserver.domain.auth.dto.response.AuthTokenResponse
import dev.euns.scenarioserver.domain.user.entity.UserEntity
import dev.euns.scenarioserver.domain.user.exception.UserErrorCode
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import dev.euns.scenarioserver.global.exception.CustomException
import dev.euns.scenarioserver.global.jwt.JwtUtil
import dev.euns.scenarioserver.global.jwt.exception.JwtErrorCode
import dev.euns.scenarioserver.global.redis.RedisService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val redisService: RedisService
) {
    fun signup(authSignupRequest: AuthSignupRequest) : BaseResponse<Unit> {
        if (userRepository.existsByUsername(authSignupRequest.username))
            throw CustomException(UserErrorCode.USER_ALREADY_EXIST)

        val user = UserEntity(
            username = authSignupRequest.username,
            password = BCryptPasswordEncoder().encode(authSignupRequest.password),
        )
        userRepository.save(user)

        return BaseResponse(
            message = "회원가입 성공"
        )
    }

    fun login(authLoginRequest: AuthLoginRequest) : BaseResponse<AuthTokenResponse> {
        val user = userRepository.findByUsername(authLoginRequest.username).orElseThrow {
            throw CustomException(UserErrorCode.USER_NOT_FOUND)
        }

        if (!BCryptPasswordEncoder().matches(authLoginRequest.password, user.password))
            throw CustomException(UserErrorCode.USER_NOT_MATCH)

        return BaseResponse(
            message = "로그인 성공",
            data = jwtUtil.generateToken(user)
        )
    }

    fun refresh(authRefreshRequest: AuthRefreshRequest) : BaseResponse<AuthTokenResponse> {
        if (authRefreshRequest.refreshToken.isEmpty())
            throw CustomException(JwtErrorCode.JWT_EMPTY_EXCEPTION)

        val id: Long = jwtUtil.getUserId(authRefreshRequest.refreshToken)
        val user = userRepository.findById(id).orElseThrow {
            throw CustomException(UserErrorCode.USER_NOT_FOUND)
        }

        val cachedToken = redisService.getRefreshToken(id)

        if (cachedToken != authRefreshRequest.refreshToken)
            throw CustomException(JwtErrorCode.JWT_TOKEN_ERROR)

        return BaseResponse(
            message = "리프레시 성공",
            data = jwtUtil.generateToken(user)
        )
    }
}