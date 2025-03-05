package dev.euns.fishingstopserver.domain.assistant.controller

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.assistant.AssistantRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import dev.euns.fishingstopserver.domain.assistant.service.AssistantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AssistantController(
    private val assistantService: AssistantService
) {
    @GetMapping("/")
    suspend fun getMessage(): String{
        return assistantService.getAssistant()
    }
}