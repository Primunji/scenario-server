package dev.euns.scenarioserver.domain.call.gateway

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import dev.euns.scenarioserver.domain.call.dto.request.CallGatewayRequestDto
import dev.euns.scenarioserver.domain.call.dto.response.CallGatewayResponseDto
import dev.euns.scenarioserver.global.config.Gateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Gateway("/call/ws")
class CallGateway(
    private val objectMapper: ObjectMapper,

    @Value("\${typecast.api-key}")
    private val apiKey: String
) : WebSocketHandler {
    private val restTemplate = RestTemplate()

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(
            session.receive()
                .flatMap { msg ->
                    Mono.fromCallable {
                        objectMapper.readValue(msg.payloadAsText, CallGatewayRequestDto::class.java)
                    }
                    .map { callMessage ->
                        val url = sendRequest(callMessage.message)
                        val response = CallGatewayResponseDto("success", url.toString())
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


    private fun sendRequest(message: String): Any {
        val mapper = ObjectMapper()
        val headers = HttpHeaders().apply {
            set("Authorization", "Bearer $apiKey")
            set("ContentType", "application/json")
        }

        val requestBody = mapOf(
            "actor_id" to "632293f759d649937b97f323",
            "text" to message,
            "lang" to "auto",
            "tempo" to 1,
            "volume" to 100,
            "pitch" to 0,
            "xapi_hd" to true,
            "max_seconds" to 60,
            "model_version" to "latest",
            "xapi_audio_format" to "mp3",
            "emotion_tone_preset" to "tonemid-1"
        )
        val entity = HttpEntity(requestBody, headers)
        val response = restTemplate.exchange("https://typecast.ai/api/speak", HttpMethod.POST, entity, String::class.java)
        val rootNode: JsonNode = mapper.readTree(response.body)
        val apiEntity = HttpEntity(null, headers)
        while (true) {
            val apiResponse = restTemplate.exchange(
                rootNode["result"]["speak_v2_url"].asText(),
                HttpMethod.GET,
                apiEntity,
                String::class.java
            )
            val audioNode: JsonNode = mapper.readTree(apiResponse.body)
            if (audioNode["result"]["status"].asText() == "done") {
                val audioUrl = audioNode["result"]["audio_download_url"].asText()

                return audioUrl
            }

            Thread.sleep(500)
        }
    }
}