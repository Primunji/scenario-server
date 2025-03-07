package dev.euns.scenarioserver.domain.auth.dto.request

data class AuthSignupRequest(
    val username: String,
    val password: String
)
