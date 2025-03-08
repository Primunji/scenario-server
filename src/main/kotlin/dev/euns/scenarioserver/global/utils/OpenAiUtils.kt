package dev.euns.scenarioserver.global.utils

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.thread.ThreadId
import com.aallam.openai.client.OpenAI
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OpenAiUtils(
    @Value("\${openai.api-key}")
    private val apiKey: String,

) {

    @OptIn(BetaOpenAI::class)
    suspend fun createThread(): String {
        val openAI = OpenAI(token = apiKey)
        val thread = openAI.thread()
        val thread_id = thread.id.id.toString()
        return thread_id
    }
}