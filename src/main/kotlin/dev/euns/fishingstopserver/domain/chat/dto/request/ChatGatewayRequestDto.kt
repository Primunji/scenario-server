package dev.euns.fishingstopserver.domain.chat.dto.request

data class ChatGatewayRequestDto(
    val accessToken: String,
    val promptId: String,
    val message: String
)