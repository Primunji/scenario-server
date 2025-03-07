package dev.euns.scenarioserver.infra.openai

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.assistant.AssistantId
import com.aallam.openai.api.assistant.AssistantRequest
import com.aallam.openai.api.core.Status
import com.aallam.openai.api.message.MessageContent
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.api.run.RunRequest
import com.aallam.openai.client.OpenAI
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value


@Component
class PromptGenerator(
    @Value("\${openai.api-key}")
    private val apiKey: String
) {
    @OptIn(BetaOpenAI::class)
    suspend fun generate(name:String,gender:String,prompt: String): String? {
        val asset_id = "asst_pqLwsGAZj9mieSFzUGXgvAZK"
        val openai = OpenAI(apiKey)

        val thread = openai.thread()

        val run = openai.createRun(
            thread.id, request = RunRequest(
                assistantId = AssistantId(asset_id),
                instructions = "입력된 이름: $name, 내용: $prompt",
            )
        )

        do {
            delay(500)
            val retrievedRun = openai.getRun(threadId = thread.id, runId = run.id)
        } while (retrievedRun.status != Status.Completed)

        val assistantMessages = openai.messages(thread.id)
        var result = ""
        for (message in assistantMessages) {
            val textContent = message.content.first() as? MessageContent.Text ?: error("Expected MessageContent.Text")
            result = textContent.text.value
        }

        val mapper = jacksonObjectMapper()
        println(result)
        println(result.trimIndent())

        val userList: PromptResult = mapper.readValue(result.trimIndent())


        if (!userList.success)
            return null
        println(userList.message)
        return userList.message
    }

}


data class PromptResult(
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("message") val message: String
)