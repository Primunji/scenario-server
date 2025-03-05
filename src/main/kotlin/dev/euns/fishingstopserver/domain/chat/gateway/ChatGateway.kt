package dev.euns.fishingstopserver.domain.chat.gateway

import com.fasterxml.jackson.databind.ObjectMapper
import dev.euns.fishingstopserver.domain.call.dto.request.CallGatewayRequestDto
import dev.euns.fishingstopserver.domain.call.dto.response.CallGatewayResponseDto
import dev.euns.fishingstopserver.global.config.Gateway
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Gateway("/chat/ws")
class ChatGateway(
    private val objectMapper: ObjectMapper,
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(
            session.receive()
                .flatMap { msg ->
                    Mono.fromCallable {
                        objectMapper.readValue(msg.payloadAsText, CallGatewayRequestDto::class.java)
                    }
                    .map { callMessage ->
//                        val url = sendRequest(callMessage.message)
                        val response = CallGatewayResponseDto("success", "url".toString())
                        objectMapper.writeValueAsString(response)
                    }
                    .onErrorResume { ex ->
                        val errorMessage = when (ex) {
                            is com.fasterxml.jackson.core.JsonParseException ->
                                "Invalid JSON format: ${ex.originalMessage}"
                            else ->
                                ex.message ?: "Unknown error"
                        }
                        val errorResponse = CallGatewayResponseDto("error", errorMessage)
                        Mono.just(objectMapper.writeValueAsString(errorResponse))
                    }
                    .map { session.textMessage(it) }
                }
        )
    }
}