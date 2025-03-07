package dev.euns.scenarioserver.domain.auth.dto.request

data class AuthLoginRequest(
    val username: String,
    val password: String
)
